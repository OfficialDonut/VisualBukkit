package us.donut.visualbukkit.editor;

import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.UndoManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {

    private static VisualBukkit visualBukkit = VisualBukkit.getInstance();
    private static Path projectsFolder = visualBukkit.getDataFolder().resolve("Projects");
    private static List<Project> projects = new ArrayList<>();
    private static Project currentProject;

    public static void loadProjects() {
        try {
            if (Files.notExists(projectsFolder)) {
                Files.createDirectory(projectsFolder);
            }

            Files.list(projectsFolder).forEach(path -> {
                if (Files.isDirectory(path)) {
                    try {
                        projects.add(new Project(path.getFileName().toString()));
                    } catch (IOException e) {
                        VisualBukkit.displayException("Failed to load project", e);
                    }
                }
            });
        } catch (IOException e) {
            VisualBukkit.displayException("Failed to load projects", e);
            Platform.exit();
        }

        if (projects.isEmpty()) {
            promptCreateProject(false);
        } else {
            Project lastProject = getProject(visualBukkit.getDataFile().getConfig().getString("last-project"));
            open(lastProject == null ? projects.get(0) : lastProject);
        }
    }

    public static void open(Project project) {
        try {
            if (currentProject != null) {
                currentProject.save();
            }
        } catch (IOException e) {
            VisualBukkit.displayException("Failed to save project", e);
        }
        UndoManager.clear();
        SplitPane splitPane = visualBukkit.getSplitPane();
        splitPane.getItems().set(1, project.getTabPane());
        splitPane.getItems().set(2, project.getProjectPane());
        splitPane.setDividerPositions(0.2, 0.825);
        currentProject = project;
    }

    public static void promptCreateProject(boolean canCancel) {
        TextInputDialog newProjectDialog = new TextInputDialog();
        newProjectDialog.setTitle("Create New Project");
        newProjectDialog.setContentText("Project name:");
        newProjectDialog.setHeaderText(null);
        newProjectDialog.setGraphic(null);

        String name = newProjectDialog.showAndWait().orElse("").replaceAll("\\s", "");

        if (projects.stream().anyMatch(project -> project.getName().equalsIgnoreCase(name))) {
            VisualBukkit.displayError("Project already exists");
            promptCreateProject(canCancel);
        } else if (name.isEmpty()) {
            if (!canCancel) {
                VisualBukkit.displayError("Invalid project name");
                promptCreateProject(false);
            }
        } else {
            try {
                Project project = new Project(name);
                projects.add(project);
                open(project);
                VisualBukkit.displayMessage("Successfully created project");
            } catch (IOException e) {
                VisualBukkit.displayException("Failed to create project", e);
            }
        }
    }

    public static void promptOpenProject() {
        ChoiceDialog<Project> openProjectDialog = new ChoiceDialog<>();
        openProjectDialog.getItems().addAll(projects);
        openProjectDialog.setTitle("Open Project");
        openProjectDialog.setContentText("Project:");
        openProjectDialog.setHeaderText(null);
        openProjectDialog.setGraphic(null);
        openProjectDialog.showAndWait().ifPresent(project -> {
            open(project);
            VisualBukkit.displayMessage("Successfully opened project");
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void promptDeleteProject() {
        ChoiceDialog<Project> deleteProjectDialog = new ChoiceDialog<>();
        deleteProjectDialog.getItems().addAll(projects);
        deleteProjectDialog.setTitle("Delete Project");
        deleteProjectDialog.setContentText("Project:");
        deleteProjectDialog.setHeaderText(null);
        deleteProjectDialog.setGraphic(null);
        deleteProjectDialog.showAndWait().ifPresent(project -> {
            try {
                MoreFiles.deleteRecursively(project.getFolder(), RecursiveDeleteOption.ALLOW_INSECURE);
                projects.remove(project);
                if (project.equals(currentProject)) {
                    currentProject = null;
                }
                VisualBukkit.displayMessage("Successfully deleted project");
                if (projects.isEmpty()) {
                    promptCreateProject(false);
                } else {
                    open(projects.get(0));
                }
            } catch (IOException e) {
                VisualBukkit.displayException("Failed to delete project", e);
            }
        });
    }

    public static Project getProject(String name) {
        for (Project project : projects) {
            if (project.getName().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public static Path getProjectsFolder() {
        return projectsFolder;
    }
}
