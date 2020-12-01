package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.gui.ProjectView;
import com.gmail.visualbukkit.util.DataFile;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Project implements Comparable<Project> {

    private String name;
    private Path folder;
    private Path resourceFolder;
    private DataFile dataFile;
    private List<BlockCanvas> canvases = new ArrayList<>();

    private String pluginName;
    private String pluginVersion;
    private String pluginAuthor;
    private String pluginDescription;
    private String pluginDependencies;
    private String pluginSoftDepend;

    public Project(String name) {
        this.name = name;

        folder = ProjectManager.getProjectsFolder().resolve(name);
        resourceFolder = folder.resolve("Resource Files");
        dataFile = new DataFile(folder.resolve("data.json"));

        if (Files.notExists(resourceFolder)) {
            try {
                Files.createDirectories(resourceFolder);
            } catch (IOException e) {
                NotificationManager.displayException("Failed to create resource folder", e);
            }
        }

        JSONObject obj = dataFile.getJson();
        pluginName = obj.optString("plugin-name", "");
        pluginVersion = obj.optString("plugin-version", "");
        pluginAuthor = obj.optString("plugin-author", "");
        pluginDescription = obj.optString("plugin-description", "");
        pluginDependencies = obj.optString("plugin-depend", "");
        pluginSoftDepend = obj.optString("plugin-soft-depend", "");

        JSONArray canvasArray = obj.optJSONArray("canvases");
        if (canvasArray != null) {
            for (Object o : canvasArray) {
                if (o instanceof JSONObject) {
                    JSONObject canvasObj = (JSONObject) o;
                    String canvasName = canvasObj.optString("name");
                    if (canvasName != null) {
                        BlockCanvas canvas = new BlockCanvas(canvasName);
                        canvas.deserialize(canvasObj);
                        canvases.add(canvas);
                    }
                }
            }
        }
        if (canvases.isEmpty()) {
            canvases.add(new BlockCanvas("Main"));
        }
    }

    public void openResourceFolder() {
        try {
            Desktop.getDesktop().browse(resourceFolder.toUri());
        } catch (IOException e) {
            NotificationManager.displayException("Failed to open resource files", e);
        }
    }

    public void promptAddCanvas() {
        TextInputDialog newCanvasDialog = new TextInputDialog();
        newCanvasDialog.setTitle("New Canvas");
        newCanvasDialog.setContentText("Canvas name:");
        newCanvasDialog.setHeaderText(null);
        newCanvasDialog.setGraphic(null);
        String canvasName = newCanvasDialog.showAndWait().orElse("");
        if (!canvasName.isBlank()) {
            BlockCanvas canvas = new BlockCanvas(canvasName);
            canvases.add(canvas);
            TabPane canvasPane = VisualBukkit.getInstance().getCanvasPane();
            Tab tab = new Tab(canvasName, canvas);
            canvasPane.getTabs().add(tab);
            canvasPane.getSelectionModel().select(tab);
            NotificationManager.displayMessage("Created canvas", "Successfully created canvas");
        }
    }

    public void save() throws IOException {
        try {
            Files.copy(dataFile.getPath(), folder.resolve("data-backup.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {}
        dataFile.clear();
        JSONObject obj = dataFile.getJson();
        JSONArray canvasArray = new JSONArray();
        for (BlockCanvas canvas : canvases) {
            JSONObject canvasObj = canvas.serialize();
            canvasObj.put("name", canvas.getName());
            canvasArray.put(canvasObj);
        }
        ProjectView projectView = VisualBukkit.getInstance().getProjectView();
        pluginName = projectView.getPluginName();
        pluginVersion = projectView.getPluginVersion();
        pluginAuthor = projectView.getPluginAuthor();
        pluginDescription = projectView.getPluginDescription();
        pluginDependencies = projectView.getPluginDepend();
        pluginSoftDepend = projectView.getPluginSoftDepend();
        obj.put("plugin-name", pluginName);
        obj.put("plugin-version", pluginVersion);
        obj.put("plugin-author", pluginAuthor);
        obj.put("plugin-description", pluginDescription);
        obj.put("plugin-depend", pluginDependencies);
        obj.put("plugin-soft-depend", pluginSoftDepend);
        obj.put("canvases", canvasArray);
        dataFile.save();
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

    public List<BlockCanvas> getCanvases() {
        return canvases;
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
