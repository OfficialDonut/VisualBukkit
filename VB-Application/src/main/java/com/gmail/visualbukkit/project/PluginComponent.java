package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.*;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Level;

public class PluginComponent implements Comparable<PluginComponent> {

    private final SimpleBooleanProperty disabled = new SimpleBooleanProperty(false);
    private final Project project;
    private final Path directory;
    private final Path dataFile;
    private final Path blockFile;
    private final UndoManager undoManager;
    private final PluginComponentPane pane;
    private PluginComponentBlock block;
    private String blockType;

    protected PluginComponent(Project project, Path directory) {
        this.project = project;
        this.directory = directory;
        dataFile = directory.resolve("data.json");
        blockFile = directory.resolve("block.json");
        undoManager = new UndoManager(this);
        pane = new PluginComponentPane();
        pane.opacityProperty().bind(Bindings.when(disabled).then(0.5).otherwise(1));

        if (Files.exists(dataFile)) {
            try {
                JSONObject json = new JSONObject(Files.readString(dataFile));
                disabled.set(json.optBoolean("disabled"));
                blockType = json.optString("block-type", null);
            } catch (IOException | JSONException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to load data file", e);
            }
        }
    }

    public PluginComponent(Project project, Path file, PluginComponentBlock block) {
        this(project, file);
        setBlock(block);
    }

    public PluginComponentBlock load() throws IOException, JSONException {
        if (!isLoaded()) {
            VisualBukkitApp.getLogger().info("Loading plugin component: " + getName());
            if (Files.exists(blockFile)) {
                setBlock(BlockRegistry.newPluginComponent(new JSONObject(Files.readString(blockFile))));
            } else {
                setBlock(new PluginComponentBlock.Unknown());
            }
        }
        block.updateState();
        return block;
    }

    public void unload() throws IOException {
        if (isLoaded() && !project.isOpen(this)) {
            save();
            setBlock(null);
        }
    }

    public void save() throws IOException {
        Files.createDirectories(directory);
        JSONObject json = new JSONObject();
        json.put("disabled", isDisabled());
        json.put("block-type", blockType);
        Files.writeString(dataFile, json.toString());
        if (isLoaded()) {
            Files.writeString(blockFile, block.serialize().toString());
        }
    }

    public void delete() throws IOException {
        MoreFiles.deleteRecursively(directory, RecursiveDeleteOption.ALLOW_INSECURE);
        setBlock(null);
    }

    public boolean containsBlock(String blockUUID) {
        if (project.isOpen(this)) {
            return (VisualBukkitApp.getPrimaryStage().getScene().lookup("#" + blockUUID) instanceof Block b) && b.getPluginComponentBlock().equals(block);
        } else {
            try {
                return Files.readString(blockFile, StandardCharsets.UTF_8).contains(blockUUID);
            } catch (IOException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to read file", e);
                return false;
            }
        }
    }

    public void setBlock(PluginComponentBlock block) {
        this.block = block;
        if (block != null) {
            blockType = block.getDefinition().id();
        }
        pane.setBlock(block);
    }

    public boolean isLoaded() {
        return block != null;
    }

    public boolean isDisabled() {
        return disabled.get();
    }

    public Optional<PluginComponentBlock> getBlock() {
        return Optional.ofNullable(block);
    }

    public Optional<String> getBlockType() {
        return Optional.ofNullable(blockType);
    }

    public PluginComponentPane getPane() {
        return pane;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public String getName() {
        return directory.getFileName().toString();
    }

    public Path getDirectory() {
        return directory;
    }

    public SimpleBooleanProperty disabledProperty() {
        return disabled;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(PluginComponent o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PluginComponent that)) return false;
        return directory.equals(that.directory);
    }

    @Override
    public int hashCode() {
        return directory.hashCode();
    }
}
