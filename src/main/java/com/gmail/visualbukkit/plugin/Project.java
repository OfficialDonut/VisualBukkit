package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.gui.ProjectView;
import com.gmail.visualbukkit.util.DataFile;
import javafx.scene.control.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Project implements Comparable<Project> {

    private String name;
    private Path folder;
    private Path canvasFolder;
    private Path resourceFolder;
    private DataFile dataFile;
    private Map<String, BlockCanvas> canvases = new LinkedHashMap<>();

    private String pluginName;
    private String pluginVersion;
    private String pluginAuthor;
    private String pluginDescription;
    private String pluginDependencies;
    private String pluginSoftDepend;

    public Project(String name) {
        this.name = name;
        initFiles();

        JSONObject obj = dataFile.getJson();
        pluginName = obj.optString("plugin-name", "");
        pluginVersion = obj.optString("plugin-version", "");
        pluginAuthor = obj.optString("plugin-author", "");
        pluginDescription = obj.optString("plugin-description", "");
        pluginDependencies = obj.optString("plugin-depend", "");
        pluginSoftDepend = obj.optString("plugin-soft-depend", "");

        JSONArray canvasNameArray = obj.optJSONArray("canvases");
        if (canvasNameArray != null) {
            for (Object o : canvasNameArray) {
                if (o instanceof String) {
                    String canvasName = (String) o;
                    DataFile canvasFile = new DataFile(canvasFolder.resolve(canvasName + ".json"));
                    BlockCanvas canvas = new BlockCanvas(canvasName);
                    canvas.deserialize(canvasFile.getJson());
                    canvases.put(canvasName, canvas);
                }
            }
        }

        if (canvases.isEmpty()) {
            canvases.put("Main", new BlockCanvas("Main"));
        }
    }

    private void initFiles() {
        folder = ProjectManager.getProjectsFolder().resolve(name);
        canvasFolder = folder.resolve("Canvases");
        resourceFolder = folder.resolve("Resource Files");
        dataFile = new DataFile(folder.resolve("data.json"));

        try {
            Files.createDirectories(canvasFolder);
            Files.createDirectories(resourceFolder);
        } catch (IOException e) {
            NotificationManager.displayException("Failed to create project folder", e);
        }
    }

    public void openResourceFolder() {
        try {
            Desktop.getDesktop().browse(resourceFolder.toUri());
        } catch (IOException e) {
            NotificationManager.displayException("Failed to open resource files", e);
        }
    }

    public void promptRename() {
        TextInputDialog renameDialog = new TextInputDialog();
        renameDialog.setTitle("Rename Project");
        renameDialog.setContentText("New name:");
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        String newName = renameDialog.showAndWait().orElse("");
        if (StringUtils.isNotBlank(newName)) {
            if (ProjectManager.exists(newName)) {
                NotificationManager.displayError("Invalid project name", "There is already a project with this name");
                promptRename();
            } else if (!StringUtils.isAlphanumeric(name)) {
                NotificationManager.displayError("Invalid project name", "Project name must be alphanumeric");
                promptRename();
            } else {
                try {
                    Files.move(folder, folder.resolveSibling(newName));
                    name = newName;
                    initFiles();
                    NotificationManager.displayMessage("Renamed project", "Successfully renamed project");
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to rename project", e);
                }
            }
        }
    }

    public void promptAddCanvas() {
        TextInputDialog newCanvasDialog = new TextInputDialog();
        newCanvasDialog.setTitle("New Canvas");
        newCanvasDialog.setContentText("Canvas name:");
        newCanvasDialog.setHeaderText(null);
        newCanvasDialog.setGraphic(null);
        String canvasName = newCanvasDialog.showAndWait().orElse("");
        if (StringUtils.isNotBlank(canvasName)) {
            if (!canvases.containsKey(canvasName)) {
                BlockCanvas canvas = new BlockCanvas(canvasName);
                canvases.put(canvasName, canvas);
                TabPane canvasPane = VisualBukkit.getInstance().getCanvasPane();
                Tab tab = new Tab(canvasName, canvas);
                canvasPane.getTabs().add(tab);
                canvasPane.getSelectionModel().select(tab);
                NotificationManager.displayMessage("Created canvas", "Successfully created canvas");
            } else {
                NotificationManager.displayError("Invalid canvas name", "There is already a canvas with this name");
                promptAddCanvas();
            }
        }
    }

    public void promptDeleteCanvas(BlockCanvas canvas) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this canvas?", ButtonType.YES, ButtonType.CANCEL);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                canvases.remove(canvas.getName());
                TabPane canvasPane = VisualBukkit.getInstance().getCanvasPane();
                canvasPane.getTabs().remove(canvasPane.getSelectionModel().getSelectedItem());
                NotificationManager.displayMessage("Deleted canvas", "Successfully deleted canvas");
            }
        });
    }

    public void promptRenameCanvas(BlockCanvas canvas) {
        TextInputDialog renameDialog = new TextInputDialog();
        renameDialog.setTitle("Rename Canvas");
        renameDialog.setContentText("New name:");
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        String newName = renameDialog.showAndWait().orElse("");
        if (StringUtils.isNotBlank(newName)) {
            if (!canvases.containsKey(newName)) {
                canvases.remove(canvas.getName());
                canvases.put(newName, canvas);
                canvas.setName(newName);
                VisualBukkit.getInstance().getCanvasPane().getSelectionModel().getSelectedItem().setText(newName);
                NotificationManager.displayMessage("Renamed canvas", "Successfully renamed canvas");
            } else {
                NotificationManager.displayError("Invalid canvas name", "There is already a canvas with this name");
                promptRenameCanvas(canvas);
            }
        }
    }

    public void save() throws IOException {
        dataFile.clear();
        JSONObject obj = dataFile.getJson();

        ProjectView projectView = VisualBukkit.getInstance().getProjectView();
        obj.put("plugin-name", pluginName = projectView.getPluginName());
        obj.put("plugin-version", pluginVersion = projectView.getPluginVersion());
        obj.put("plugin-author", pluginAuthor = projectView.getPluginAuthor());
        obj.put("plugin-description", pluginDescription = projectView.getPluginDescription());
        obj.put("plugin-depend", pluginDependencies = projectView.getPluginDepend());
        obj.put("plugin-soft-depend", pluginSoftDepend = projectView.getPluginSoftDepend());

        JSONArray canvasNameArray = new JSONArray();
        for (Tab tab : VisualBukkit.getInstance().getCanvasPane().getTabs()) {
            canvasNameArray.put(tab.getText());
        }
        obj.put("canvases", canvasNameArray);

        dataFile.save();

        Path backupDir = canvasFolder.resolve("Backup");
        Files.createDirectories(backupDir);
        FileUtils.cleanDirectory(backupDir.toFile());
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(canvasFolder)) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    Files.move(path, backupDir.resolve(path.getFileName()));
                }
            }
        }

        for (BlockCanvas canvas : canvases.values()) {
            DataFile canvasFile = new DataFile(canvasFolder.resolve(canvas.getName() + ".json"));
            canvasFile.setJson(canvas.serialize());
            canvasFile.save();
        }
    }

    @Override
    public int compareTo(Project project) {
        return name.compareTo(project.name);
    }

    public String getName() {
        return name;
    }

    public Path getFolder() {
        return folder;
    }

    public Path getResourceFolder() {
        return resourceFolder;
    }

    public Collection<BlockCanvas> getCanvases() {
        return Collections.unmodifiableCollection(canvases.values());
    }

    public String getPluginName() {
        return pluginName.trim();
    }

    public String getPluginVersion() {
        return pluginVersion.trim();
    }

    public String getPluginAuthor() {
        return pluginAuthor.trim();
    }

    public String getPluginDescription() {
        return pluginDescription.trim();
    }

    public String getPluginDependencies() {
        return pluginDependencies.trim();
    }

    public String getPluginSoftDepend() {
        return pluginSoftDepend.trim();
    }
}
