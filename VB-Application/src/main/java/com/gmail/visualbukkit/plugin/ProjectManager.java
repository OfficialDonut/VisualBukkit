package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.lang.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectManager {

    private static VisualBukkitApp visualBukkit = VisualBukkitApp.getInstance();
    private static Path projectsDir = visualBukkit.getDataDir().resolve("Projects");
    private static ObjectProperty<Project> currentProject = new SimpleObjectProperty<>();

    public static void openLast() {
        Set<String> projects = getProjects();
        if (projects.isEmpty()) {
            promptCreateProject(false);
        } else {
            String lastProject = visualBukkit.getDataFile().getJson().optString("last-project", "");
            open(projects.contains(lastProject) ? lastProject : projects.iterator().next());
        }
    }

    public static void open(String projectName) {
        if (getCurrentProject() != null) {
            try {
                getCurrentProject().save();
            } catch (IOException e) {
                NotificationManager.displayException("Failed to save current project", e);
                return;
            }
        }
        try {
            Project project = new Project(projectsDir.resolve(projectName));
            currentProject.set(project);
            visualBukkit.getDataFile().getJson().put("last-project", projectName);
            visualBukkit.setPluginComponentTabPane(project.getPluginComponentTabPane());
            DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("Developing " + projectName).setStartTimestamps(System.currentTimeMillis()).build());
        } catch (IOException e) {
            NotificationManager.displayException("Failed to read project file", e);
        }
    }

    public static void promptCreateProject(boolean canCancel) {
        TextInputDialog newDialog = new TextInputDialog();
        newDialog.setTitle(VisualBukkitApp.getString("dialog.new_project.title"));
        newDialog.setContentText(VisualBukkitApp.getString("dialog.new_project.content"));
        newDialog.setHeaderText(null);
        newDialog.setGraphic(null);

        String name = newDialog.showAndWait().orElse("");
        if (isNameValid(name)) {
            open(name);
        } else if (!canCancel) {
            promptCreateProject(false);
        }
    }

    public static void promptOpenProject(boolean canCancel) {
        ChoiceDialog<String> openDialog = new ChoiceDialog<>();
        openDialog.getItems().addAll(getProjects());
        openDialog.setTitle(VisualBukkitApp.getString("dialog.open_project.title"));
        openDialog.setContentText(VisualBukkitApp.getString("dialog.open_project.content"));
        openDialog.setHeaderText(null);
        openDialog.setGraphic(null);
        openDialog.showAndWait().ifPresentOrElse(ProjectManager::open, () -> {
            if (!canCancel) {
                promptOpenProject(false);
            }
        });
    }

    public static void promptRenameProject() {
        TextInputDialog renameDialog = new TextInputDialog();
        renameDialog.setTitle(VisualBukkitApp.getString("dialog.rename_project.title"));
        renameDialog.setContentText(VisualBukkitApp.getString("dialog.rename_project.content"));
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        renameDialog.showAndWait().ifPresent(name -> {
            if (isNameValid(name)) {
                try {
                    getCurrentProject().save();
                    if (Files.exists(getCurrentProject().getDir())) {
                        Files.move(getCurrentProject().getDir(), projectsDir.resolve(name));
                    }
                    currentProject.set(null);
                    open(name);
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to rename project", e);
                }
            }
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void promptDeleteProject() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, VisualBukkitApp.getString("dialog.confirm_delete_project"));
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    if (Files.exists(getCurrentProject().getDir())) {
                        MoreFiles.deleteRecursively(getCurrentProject().getDir(), RecursiveDeleteOption.ALLOW_INSECURE);
                    }
                    currentProject.set(null);
                    if (getProjects().isEmpty()) {
                        promptCreateProject(false);
                    } else {
                        promptOpenProject(false);
                    }
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to delete project", e);
                }
            }
        });
    }

    public static void promptImportProject() {
        TextInputDialog importDialog = new TextInputDialog();
        importDialog.setTitle(VisualBukkitApp.getString("dialog.import_project.title"));
        importDialog.setContentText(VisualBukkitApp.getString("dialog.import_project.content"));
        importDialog.setHeaderText(null);
        importDialog.setGraphic(null);
        importDialog.showAndWait().ifPresent(name -> {
            if (isNameValid(name)) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
                File zipFile = fileChooser.showOpenDialog(VisualBukkitApp.getInstance().getPrimaryStage());
                if (zipFile != null) {
                    ZipUtil.unpack(zipFile, projectsDir.resolve(name).toFile());
                    open(name);
                }
            }
        });
    }

    public static void promptExportProject() {
        try {
            getCurrentProject().save();
            File projectDir = getCurrentProject().getDir().toFile();
            if (!projectDir.exists()) {
                projectDir.mkdir();
            }
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File outputDir = directoryChooser.showDialog(VisualBukkitApp.getInstance().getPrimaryStage());
            if (outputDir != null) {
                File zipFile = new File(outputDir, UUID.randomUUID() + ".zip");
                ZipUtil.pack(projectDir, zipFile);
                VisualBukkitApp.getInstance().openDirectory(zipFile.toPath());
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to export project", e);
        }
    }

    private static boolean isNameValid(String name) {
        if (name.isBlank()) {
            return false;
        }
        if (!StringUtils.isAlphanumeric(name)) {
            NotificationManager.displayError(VisualBukkitApp.getString("error.invalid_project_name.title"), VisualBukkitApp.getString("error.invalid_project_name.content"));
            return false;
        }
        if (Files.exists(projectsDir.resolve(name))) {
            NotificationManager.displayError(VisualBukkitApp.getString("error.duplicate_project.title"), VisualBukkitApp.getString("error.duplicate_project.content"));
            return false;
        }
        return true;
    }

    private static Set<String> getProjects() {
        try (Stream<Path> pathStream = Files.list(projectsDir)) {
            return pathStream.filter(Files::isDirectory).map(path -> path.getFileName().toString()).collect(Collectors.toSet());
        } catch (IOException e) {
            return Collections.emptySet();
        }
    }

    public static Project getCurrentProject() {
        return currentProject.get();
    }

    public static ReadOnlyObjectProperty<Project> currentProjectProperty() {
        return currentProject;
    }
}
