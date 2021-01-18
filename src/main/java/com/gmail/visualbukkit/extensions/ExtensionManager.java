package com.gmail.visualbukkit.extensions;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ExtensionManager {

    private static Path extensionsFolder = VisualBukkit.getDataFolder().resolve("Extensions");
    private static Path installFolder = extensionsFolder.resolve("Install");
    private static Path uninstallFile = extensionsFolder.resolve("uninstall.txt");
    private static Set<String> uninstallSet = new HashSet<>();
    private static Map<VisualBukkitExtension, Path> extensions = new HashMap<>();
    private static ExtensionManagerStage managerStage = new ExtensionManagerStage();

    public static void init() {
        try {
            if (Files.exists(uninstallFile)) {
                for (String fileName : Files.readAllLines(uninstallFile, StandardCharsets.UTF_8)) {
                    Files.deleteIfExists(extensionsFolder.resolve(fileName));
                }
                Files.delete(uninstallFile);
            }

            Files.createDirectories(installFolder);
            Set<Path> installSet = new HashSet<>();
            try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(installFolder)) {
                for (Path path : pathStream) {
                    Path newPath = extensionsFolder.resolve(path.getFileName());
                    installSet.add(newPath);
                    Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            Map<String, Path> loadMap = new HashMap<>();
            try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(extensionsFolder)) {
                for (Path path : pathStream) {
                    if (path.toString().endsWith(".jar")) {
                        try (JarFile jarFile = new JarFile(path.toFile())) {
                            Manifest manifest = jarFile.getManifest();
                            String mainClassName = manifest.getMainAttributes().getValue("main-class");
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

            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            for (Map.Entry<String, Path> entry : loadMap.entrySet()) {
                try {
                    method.invoke(classLoader, entry.getValue().toUri().toURL());
                    Class<?> mainClass = Class.forName(entry.getKey());
                    if (VisualBukkitExtension.class.isAssignableFrom(mainClass)) {
                        VisualBukkitExtension extension = (VisualBukkitExtension) mainClass.getConstructor().newInstance();
                        extensions.put(extension, entry.getValue());
                    }
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                    NotificationManager.displayException("Failed to load extension", e);
                }
            }
        } catch (IOException | NoSuchMethodException e) {
            NotificationManager.displayException("Failed to load extensions", e);
            Platform.exit();
        }
    }

    public static void promptManage() {
        managerStage.open();
    }

    private static class ExtensionManagerStage extends Stage {

        private ListView<VisualBukkitExtension> listView = new ListView<>();
        private SplitPane splitPane = new SplitPane(listView);
        private Map<VisualBukkitExtension, ScrollPane> extensionPanes = new HashMap<>();

        public ExtensionManagerStage() {
            listView.setPlaceholder(new Label("No extensions"));
            listView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue != null) {
                    ScrollPane scrollPane = extensionPanes.computeIfAbsent(newValue, k -> {
                        GridPane gridPane = new GridPane();
                        gridPane.addColumn(0, new Label("Name:"), new Label("Version:"), new Label("Author:"), new Label("Description:"));
                        gridPane.addColumn(1, new Label(k.getName()), new Label(k.getVersion()), new Label(k.getAuthor()), new Label(k.getDescription()));
                        gridPane.setPadding(new Insets(10));
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        ColumnConstraints columnConstraints = new ColumnConstraints();
                        columnConstraints.setPercentWidth(30);
                        gridPane.getColumnConstraints().add(columnConstraints);
                        for (Node child : gridPane.getChildren()) {
                            GridPane.setValignment(child, VPos.TOP);
                        }
                        k.setupManager(gridPane);
                        ScrollPane pane = new ScrollPane(gridPane);
                        pane.setFitToWidth(true);
                        pane.setFitToHeight(true);
                        return pane;
                    });
                    if (splitPane.getItems().size() == 2) {
                        splitPane.getItems().set(1, scrollPane);
                    } else {
                        splitPane.getItems().add(scrollPane);
                    }
                } else if (splitPane.getItems().size() == 2) {
                    splitPane.getItems().remove(1);
                }
            });

            Button installButton = new Button("Install");
            Button uninstallButton = new Button("Uninstall");
            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> close());
            CenteredHBox buttonBox = new CenteredHBox(10, installButton, uninstallButton, closeButton);
            buttonBox.setPadding(new Insets(5));

            installButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar", "*.jar"));
                List<File> jarFiles = fileChooser.showOpenMultipleDialog(VisualBukkit.getInstance().getPrimaryStage());
                if (jarFiles != null && jarFiles.size() > 0) {
                    for (File jarFile : jarFiles) {
                        try {
                            Files.copy(jarFile.toPath(), installFolder.resolve(jarFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ex) {
                            NotificationManager.displayException("Failed to install extension", ex);
                        }
                    }
                    NotificationManager.displayMessage("Installed Extensions", "Restart Visual Bukkit to complete installation");
                }
            });

            uninstallButton.setOnAction(e -> {
                if (uninstallButton.getOpacity() == 1) {
                    VisualBukkitExtension extension = listView.getSelectionModel().getSelectedItem();
                    uninstallSet.add(extensions.remove(extension).getFileName().toString());
                    listView.getItems().remove(extension);
                    try {
                        Files.write(uninstallFile, String.join("\n", uninstallSet).getBytes(StandardCharsets.UTF_8));
                        NotificationManager.displayMessage("Uninstalled extension", "Restart Visual Bukkit to complete uninstallation");
                    } catch (IOException ex) {
                        NotificationManager.displayException("Failed to uninstall extension", ex);
                    }
                }
            });

            uninstallButton.opacityProperty().bind(Bindings
                    .when(listView.getSelectionModel().selectedItemProperty().isNull())
                    .then(0.5)
                    .otherwise(1));

            BorderPane rootPane = new BorderPane();
            rootPane.setStyle("-fx-border-color: black");
            rootPane.setCenter(splitPane);
            rootPane.setBottom(buttonBox);
            Scene scene = new Scene(rootPane, 900, 600);
            scene.getStylesheets().add("/style.css");

            initOwner(VisualBukkit.getInstance().getPrimaryStage());
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Extension Manager");
            setScene(scene);
        }

        public void open() {
            if (splitPane.getItems().size() == 2) {
                splitPane.getItems().remove(1);
            }
            listView.getItems().setAll(extensions.keySet());
            show();
        }
    }
}
