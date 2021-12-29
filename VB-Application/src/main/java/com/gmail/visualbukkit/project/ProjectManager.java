package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.NotificationManager;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
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

    private static Path projectsDir = VisualBukkitApp.getDataDir().resolve("Projects");
    private static Project currentProject;

    public static void openLast() {
        Set<String> projects = getProjects();
        if (projects.isEmpty()) {
            promptCreateProject(false);
        } else {
            String lastProject = VisualBukkitApp.getData().optString("last-project", "");
            open(projects.contains(lastProject) ? lastProject : projects.iterator().next());
        }
    }

    public static void open(String projectName) {
        if (currentProject != null) {
            try {
                currentProject.close();
            } catch (IOException e) {
                NotificationManager.displayException("Failed to save current project", e);
                return;
            }
        }
        try {
            currentProject = new Project(projectsDir.resolve(projectName));
            currentProject.open();
        } catch (IOException e) {
            NotificationManager.displayException("Failed to load project", e);
        }
    }

    public static void promptCreateProject(boolean canCancel) {
        TextInputDialog newDialog = new TextInputDialog();
        VisualBukkitApp.getSettingsManager().style(newDialog.getDialogPane());
        newDialog.setTitle(LanguageManager.get("dialog.new_project.title"));
        newDialog.setContentText(LanguageManager.get("dialog.new_project.content"));
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
        VisualBukkitApp.getSettingsManager().style(openDialog.getDialogPane());
        openDialog.getItems().addAll(getProjects());
        openDialog.setTitle(LanguageManager.get("dialog.open_project.title"));
        openDialog.setContentText(LanguageManager.get("dialog.open_project.content"));
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
        VisualBukkitApp.getSettingsManager().style(renameDialog.getDialogPane());
        renameDialog.setTitle(LanguageManager.get("dialog.rename_project.title"));
        renameDialog.setContentText(LanguageManager.get("dialog.rename_project.content"));
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        renameDialog.showAndWait().ifPresent(name -> {
            if (isNameValid(name)) {
                try {
                    currentProject.save();
                    if (Files.exists(currentProject.getDir())) {
                        Files.move(currentProject.getDir(), projectsDir.resolve(name));
                    }
                    currentProject = null;
                    open(name);
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to rename project", e);
                }
            }
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void promptDeleteProject() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LanguageManager.get("dialog.confirm_delete_project"));
        VisualBukkitApp.getSettingsManager().style(alert.getDialogPane());
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    if (Files.exists(currentProject.getDir())) {
                        MoreFiles.deleteRecursively(currentProject.getDir(), RecursiveDeleteOption.ALLOW_INSECURE);
                    }
                    currentProject = null;
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
        VisualBukkitApp.getSettingsManager().style(importDialog.getDialogPane());
        importDialog.setTitle(LanguageManager.get("dialog.import_project.title"));
        importDialog.setContentText(LanguageManager.get("dialog.import_project.content"));
        importDialog.setHeaderText(null);
        importDialog.setGraphic(null);
        importDialog.showAndWait().ifPresent(name -> {
            if (isNameValid(name)) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
                File zipFile = fileChooser.showOpenDialog(VisualBukkitApp.getStage());
                if (zipFile != null) {
                    ZipUtil.unpack(zipFile, projectsDir.resolve(name).toFile());
                    open(name);
                }
            }
        });
    }

    public static void promptExportProject() {
        try {
            currentProject.save();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File outputDir = directoryChooser.showDialog(VisualBukkitApp.getStage());
            if (outputDir != null) {
                File zipFile = new File(outputDir, UUID.randomUUID() + ".zip");
                ZipUtil.pack(currentProject.getDir().toFile(), zipFile);
                VisualBukkitApp.openDirectory(zipFile.toPath());
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
            NotificationManager.displayError(LanguageManager.get("error.invalid_project_name.title"), LanguageManager.get("error.invalid_project_name.content"));
            return false;
        }
        if (Files.exists(projectsDir.resolve(name))) {
            NotificationManager.displayError(LanguageManager.get("error.duplicate_project.title"), LanguageManager.get("error.duplicate_project.content"));
            return false;
        }
        return true;
    }

    private static Set<String> getProjects() {
        try (Stream<Path> pathStream = Files.list(projectsDir)) {
            return pathStream.filter(Files::isDirectory).map(path -> path.getFileName().toString()).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    public static Project getCurrentProject() {
        return currentProject;
    }
}
