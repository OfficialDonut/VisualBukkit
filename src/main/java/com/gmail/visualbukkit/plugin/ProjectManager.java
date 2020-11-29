package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.lang.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProjectManager {

    private static Path projectsFolder = VisualBukkit.getDataFolder().resolve("Projects");
    private static List<String> projects = new ArrayList<>();
    private static Project currentProject;

    public static void init() {
        try {
            if (Files.notExists(projectsFolder)) {
                Files.createDirectory(projectsFolder);
            }
            try (Stream<Path> pathStream = Files.list(projectsFolder)) {
                pathStream.forEach(path -> {
                    if (Files.isDirectory(path)) {
                        projects.add(path.getFileName().toString());
                    }
                });
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to load projects", e);
            Platform.exit();
        }

        if (projects.isEmpty()) {
            promptCreateProject(false);
        } else {
            String lastProject = VisualBukkit.getDataFile().getJson().optString("last-project");
            open(projects.contains(lastProject) ? lastProject : projects.get(0));
        }
    }

    public static void open(String projectName) {
        Project project = new Project(projectName);
        try {
            if (currentProject != null) {
                currentProject.save();
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to save project", e);
        }
        VisualBukkit.getInstance().getProjectView().show(project);
        TabPane canvasPane = VisualBukkit.getInstance().getCanvasPane();
        canvasPane.getTabs().clear();
        for (BlockCanvas canvas : project.getCanvases()) {
            canvasPane.getTabs().add(new Tab(canvas.getName(), canvas));
        }
        currentProject = project;
        VisualBukkit.getDataFile().getJson().put("last-project", project.getName());
    }

    public static void promptCreateProject(boolean canCancel) {
        TextInputDialog newProjectDialog = new TextInputDialog();
        newProjectDialog.setTitle("Create New Project");
        newProjectDialog.setContentText("Project name:");
        newProjectDialog.setHeaderText(null);
        newProjectDialog.setGraphic(null);

        String name = newProjectDialog.showAndWait().orElse("");
        if (name.isBlank()) {
            if (!canCancel) {
                NotificationManager.displayError("Invalid project name", "Project name cannot be empty");
                promptCreateProject(false);
            }
            return;
        }

        if (projects.stream().anyMatch(projectName -> projectName.equalsIgnoreCase(name))) {
            NotificationManager.displayError("Invalid project name", "There is already a project with this name");
            promptCreateProject(canCancel);
        } else if (!StringUtils.isAlphanumeric(name)) {
            NotificationManager.displayError("Invalid project name", "Project name must be alphanumeric");
            promptCreateProject(canCancel);
        } else {
            projects.add(name);
            open(name);
            NotificationManager.displayMessage("Created project", "Successfully created project");
        }
    }

    public static void promptOpenProject() {
        ChoiceDialog<String> openProjectDialog = new ChoiceDialog<>();
        openProjectDialog.getItems().addAll(projects);
        openProjectDialog.setTitle("Open Project");
        openProjectDialog.setContentText("Project:");
        openProjectDialog.setHeaderText(null);
        openProjectDialog.setGraphic(null);
        openProjectDialog.showAndWait().ifPresent(project -> {
            open(project);
            NotificationManager.displayMessage("Opened project", "Successfully opened project");
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void promptDeleteProject() {
        ChoiceDialog<String> deleteProjectDialog = new ChoiceDialog<>();
        deleteProjectDialog.getItems().addAll(projects);
        deleteProjectDialog.setTitle("Delete Project");
        deleteProjectDialog.setContentText("Project:");
        deleteProjectDialog.setHeaderText(null);
        deleteProjectDialog.setGraphic(null);
        deleteProjectDialog.showAndWait().ifPresent(project -> {
            try {
                MoreFiles.deleteRecursively(projectsFolder.resolve(project), RecursiveDeleteOption.ALLOW_INSECURE);
                projects.remove(project);
                if (project.equals(currentProject.getName())) {
                    currentProject = null;
                }
                NotificationManager.displayMessage("Deleted project", "Successfully deleted project");
                if (projects.isEmpty()) {
                    promptCreateProject(false);
                } else {
                    open(projects.get(0));
                }
            } catch (IOException e) {
                NotificationManager.displayException("Failed to delete project", e);
            }
        });
    }

    public static void promptImportProject() {
        TextInputDialog importProjectDialog = new TextInputDialog();
        importProjectDialog.setTitle("Import Project");
        importProjectDialog.setContentText("Project name:");
        importProjectDialog.setHeaderText(null);
        importProjectDialog.setGraphic(null);

        String name = importProjectDialog.showAndWait().orElse("");
        if (name.isBlank()) {
            return;
        }

        if (projects.stream().anyMatch(projectName -> projectName.equalsIgnoreCase(name))) {
            NotificationManager.displayError("Invalid project name", "There is already a project with this name");
            promptImportProject();
        } else if (!StringUtils.isAlphanumeric(name)) {
            NotificationManager.displayError("Invalid project name", "Project name must be alphanumeric");
            promptImportProject();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
            File zipFile = fileChooser.showOpenDialog(VisualBukkit.getInstance().getPrimaryStage());
            if (zipFile != null) {
                ZipUtil.unpack(zipFile, projectsFolder.resolve(name).toFile());
                projects.add(name);
                open(name);
                NotificationManager.displayMessage("Imported project", "Successfully imported project");
            }
        }
    }

    public static void promptExportProject() {
        ChoiceDialog<String> exportProjectDialog = new ChoiceDialog<>();
        exportProjectDialog.getItems().addAll(projects);
        exportProjectDialog.setTitle("Export Project");
        exportProjectDialog.setContentText("Project:");
        exportProjectDialog.setHeaderText(null);
        exportProjectDialog.setGraphic(null);
        exportProjectDialog.showAndWait().ifPresent(project -> {
            File projectDir = projectsFolder.resolve(project).toFile();
            if (projectDir.exists()) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File outputDir = directoryChooser.showDialog(VisualBukkit.getInstance().getPrimaryStage());
                if (outputDir != null) {
                    if (currentProject != null && currentProject.getName().equalsIgnoreCase(project)) {
                        try {
                            currentProject.save();
                        } catch (IOException ignored) {}
                    }
                    File zipFile = new File(outputDir, project + ".zip");
                    ZipUtil.pack(projectDir, zipFile);
                    NotificationManager.displayMessage("Exported project", "Successfully exported project\n(" + zipFile.toPath() + ")");
                }
            }
        });
    }


    public static Path getProjectsFolder() {
        return projectsFolder;
    }

    public static Project getCurrentProject() {
        return currentProject;
    }
}
