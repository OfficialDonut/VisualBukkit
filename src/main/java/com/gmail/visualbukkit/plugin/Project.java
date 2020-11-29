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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.TreeSet;

public class Project implements Comparable<Project> {

    private String name;
    private Path folder;
    private DataFile dataFile;
    private Set<BlockCanvas> canvases = new TreeSet<>();

    private String pluginName;
    private String pluginVersion;
    private String pluginAuthor;
    private String pluginDescription;
    private String pluginDependencies;
    private String pluginSoftDepend;
    private String pluginConfig;

    public Project(String name) {
        this.name = name;

        folder = ProjectManager.getProjectsFolder().resolve(name);
        dataFile = new DataFile(folder.resolve("data.json"));

        JSONObject obj = dataFile.getJson();
        pluginName = obj.optString("plugin-name", "");
        pluginVersion = obj.optString("plugin-version", "");
        pluginAuthor = obj.optString("plugin-author", "");
        pluginDescription = obj.optString("plugin-description", "");
        pluginDependencies = obj.optString("plugin-depend", "");
        pluginSoftDepend = obj.optString("plugin-soft-depend", "");
        pluginConfig = obj.optString("plugin-config", "");

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
            int i = 0;
            while (i < canvasPane.getTabs().size() && tab.getText().compareTo(canvasPane.getTabs().get(i).getText()) > 0) {
                i++;
            }
            canvasPane.getTabs().add(i, tab);
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
        pluginConfig = projectView.getPluginConfig();
        obj.put("plugin-name", pluginName);
        obj.put("plugin-version", pluginVersion);
        obj.put("plugin-author", pluginAuthor);
        obj.put("plugin-description", pluginDescription);
        obj.put("plugin-depend", pluginDependencies);
        obj.put("plugin-soft-depend", pluginSoftDepend);
        obj.put("plugin-config", pluginConfig);
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

    public Set<BlockCanvas> getCanvases() {
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

    public String getPluginConfig() {
        return pluginConfig.trim();
    }
}
