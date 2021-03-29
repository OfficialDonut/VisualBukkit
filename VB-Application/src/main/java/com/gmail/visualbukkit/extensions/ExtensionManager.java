package com.gmail.visualbukkit.extensions;

import com.gmail.visualbukkit.SettingsManager;
import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.gui.NotificationManager;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ExtensionManager {

    private static Path extensionsDir = VisualBukkitApp.getInstance().getDataDir().resolve("Extensions");
    private static Path installDir = extensionsDir.resolve("Install");
    private static Map<VisualBukkitExtension, Path> extensions = new HashMap<>();
    private static ExtensionViewer extensionViewer = new ExtensionViewer();

    public static void loadExtensions() throws IOException {
        Files.createDirectories(extensionsDir);

        Set<Path> installSet = new HashSet<>();
        if (Files.exists(installDir)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(installDir)) {
                for (Path path : dirStream) {
                    Path newPath = extensionsDir.resolve(path.getFileName());
                    installSet.add(newPath);
                    Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }

        Map<String, Path> loadMap = new HashMap<>();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(extensionsDir)) {
            for (Path path : dirStream) {
                if (path.toString().endsWith(".jar")) {
                    try (JarFile jarFile = new JarFile(path.toFile())) {
                        Manifest manifest = jarFile.getManifest();
                        String mainClassName = manifest.getMainAttributes().getValue("main-class");
                        jarFile.close();
                        if (mainClassName != null) {
                            if (loadMap.containsKey(mainClassName)) {
                                if (installSet.contains(path)) {
                                    Files.delete(loadMap.get(mainClassName));
                                    loadMap.put(mainClassName, path);
                                } else {
                                    Files.delete(path);
                                }
                            } else {
                                loadMap.put(mainClassName, path);
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, Path> entry : loadMap.entrySet()) {
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{entry.getValue().toUri().toURL()})) {
                Class<?> mainClass = Class.forName(entry.getKey(), true, classLoader);
                if (VisualBukkitExtension.class.isAssignableFrom(mainClass)) {
                    extensions.put((VisualBukkitExtension) mainClass.getConstructor().newInstance(), entry.getValue());
                }
            } catch (Exception e) {
                NotificationManager.displayException("Failed to load extension: " + entry.getValue().getFileName() + "\nIt will be uninstalled.", e);
                Files.delete(entry.getValue());
            }
        }
    }

    public static void promptInstall() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar", "*.jar"));
        List<File> jarFiles = fileChooser.showOpenMultipleDialog(VisualBukkitApp.getInstance().getPrimaryStage());
        if (jarFiles != null && jarFiles.size() > 0) {
            try {
                Files.createDirectories(installDir);
                for (File jarFile : jarFiles) {
                    Files.copy(jarFile.toPath(), installDir.resolve(jarFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                }
                NotificationManager.displayMessage(VisualBukkitApp.getString("message.installed_extension.title"), VisualBukkitApp.getString("message.installed_extension.content"));
            } catch (IOException e) {
                NotificationManager.displayException("Failed to install extension", e);
            }
        }
    }

    public static void openViewer() {
        extensionViewer.open();
    }

    private static class ExtensionViewer extends Stage {

        private ListView<VisualBukkitExtension> listView = new ListView<>();
        private SplitPane splitPane = new SplitPane(listView);
        private Map<VisualBukkitExtension, ScrollPane> extensionPanes = new HashMap<>();

        public ExtensionViewer() {
            listView.setPlaceholder(new Label(VisualBukkitApp.getString("label.no_extensions")));
            listView.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                if (newValue != null) {
                    splitPane.getItems().setAll(listView, extensionPanes.computeIfAbsent(newValue, k -> {
                        Button uninstallButton = new Button(VisualBukkitApp.getString("button.uninstall_extension"));
                        uninstallButton.setOnAction(e -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, VisualBukkitApp.getString("dialog.confirm_uninstall_extension"));
                            alert.setHeaderText(null);
                            alert.setGraphic(null);
                            alert.showAndWait().ifPresent(buttonType -> {
                                if (buttonType == ButtonType.OK) {
                                    try {
                                        Files.delete(extensions.remove(k));
                                        open();
                                        NotificationManager.displayMessage(VisualBukkitApp.getString("message.uninstalled_extension.title"), VisualBukkitApp.getString("message.uninstalled_extension.content"));
                                    } catch (IOException ex) {
                                        NotificationManager.displayException("Failed to uninstall extension", ex);
                                    }
                                }
                            });
                        });
                        GridPane gridPane = new GridPane();
                        gridPane.getStyleClass().add("extension-info-grid");
                        gridPane.addColumn(0, new Label("Name:"), new Label("Version:"), new Label("Author:"), new Label("Description:"));
                        gridPane.addColumn(1, new Label(k.getName()), new Label(k.getVersion()), new Label(k.getAuthor()), new Label(k.getDescription()));
                        gridPane.addRow(4, uninstallButton);
                        ColumnConstraints columnConstraints = new ColumnConstraints();
                        columnConstraints.setPercentWidth(30);
                        gridPane.getColumnConstraints().add(columnConstraints);
                        for (Node child : gridPane.getChildren()) {
                            GridPane.setValignment(child, VPos.TOP);
                        }
                        k.setupInfoPane(gridPane);
                        ScrollPane pane = new ScrollPane(gridPane);
                        pane.setFitToWidth(true);
                        pane.setFitToHeight(true);
                        return pane;
                    }));
                } else {
                    splitPane.getItems().setAll(listView);
                }
            });

            SettingsManager.getInstance().bindStyle(splitPane);
            Scene scene = new Scene(splitPane, 900, 600);

            initOwner(VisualBukkitApp.getInstance().getPrimaryStage());
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Extension Viewer");
            setScene(scene);
        }

        public void open() {
            splitPane.getItems().setAll(listView);
            listView.getItems().setAll(extensions.keySet());
            if (!listView.getItems().isEmpty()) {
                listView.getSelectionModel().selectFirst();
            }
            show();
        }
    }
}
