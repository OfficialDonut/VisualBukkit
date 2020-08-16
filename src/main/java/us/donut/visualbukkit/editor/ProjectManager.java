package us.donut.visualbukkit.editor;

import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.zeroturnaround.zip.ZipUtil;
import us.donut.visualbukkit.VisualBukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProjectManager {

    private static Path projectsFolder = VisualBukkit.DATA_FOLDER.resolve("Projects");
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
            VisualBukkit.displayException("Failed to load projects", e);
            Platform.exit();
        }

        if (projects.isEmpty()) {
            promptCreateProject(false);
        } else {
            String lastProject = VisualBukkit.DATA_FILE.getString("last-project");
            open(lastProject == null ? projects.get(0) : lastProject);
        }
    }

    public static void open(String projectName) {
        Project project = new Project(projectName);
        try {
            if (currentProject != null) {
                currentProject.save();
            }
        } catch (IOException e) {
            VisualBukkit.displayException("Failed to save project", e);
        }
        SplitPane splitPane = VisualBukkit.getInstance().getSplitPane();
        splitPane.getItems().set(1, project.getTabPane());
        splitPane.getItems().set(2, project.getProjectPane());
        splitPane.setDividerPositions(0.2, 0.825);
        currentProject = project;
        VisualBukkit.DATA_FILE.set("last-project", currentProject.getName());
    }

    public static void promptCreateProject(boolean canCancel) {
        TextInputDialog newProjectDialog = new TextInputDialog();
        newProjectDialog.setTitle("Create New Project");
        newProjectDialog.setContentText("Project name:");
        newProjectDialog.setHeaderText(null);
        newProjectDialog.setGraphic(null);

        String name = newProjectDialog.showAndWait().orElse("").replaceAll("\\s", "");

        if (projects.stream().anyMatch(projectName -> projectName.equalsIgnoreCase(name))) {
            VisualBukkit.displayError("Invalid project name", "There is already a project with this name");
            promptCreateProject(canCancel);
        } else if (name.isEmpty()) {
            if (!canCancel) {
                VisualBukkit.displayError("Invalid project name", "Project name cannot be empty");
                promptCreateProject(false);
            }
        } else {
            projects.add(name);
            open(name);
            VisualBukkit.displayMessage("Created project", "Successfully created project");
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
            VisualBukkit.displayMessage("Opened project", "Successfully opened project");
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
                VisualBukkit.displayMessage("Deleted project", "Successfully deleted project");
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

    public static void promptImportProject() {
        TextInputDialog importProjectDialog = new TextInputDialog();
        importProjectDialog.setTitle("Import Project");
        importProjectDialog.setContentText("Project name:");
        importProjectDialog.setHeaderText(null);
        importProjectDialog.setGraphic(null);

        String name = importProjectDialog.showAndWait().orElse("").replaceAll("\\s", "");

        if (projects.stream().anyMatch(projectName -> projectName.equalsIgnoreCase(name))) {
            VisualBukkit.displayError("Invalid project name", "There is already a project with this name");
            promptImportProject();
        } else if (!name.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
            File zipFile = fileChooser.showOpenDialog(VisualBukkit.getInstance().getPrimaryStage());
            if (zipFile != null) {
                ZipUtil.unpack(zipFile, projectsFolder.resolve(name).toFile());
                projects.add(name);
                open(name);
                VisualBukkit.displayMessage("Imported project", "Successfully imported project");
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
                    VisualBukkit.displayMessage("Exported project", "Successfully exported project\n(" + zipFile.toPath() + ")");
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
