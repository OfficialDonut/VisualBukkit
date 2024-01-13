package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.*;
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
    private final Path file;
    private final UndoManager undoManager;
    private final PluginComponentPane pane;
    private PluginComponentBlock block;

    protected PluginComponent(Project project, Path file) {
        this.project = project;
        this.file = file;
        undoManager = new UndoManager(this);
        pane = new PluginComponentPane();
        pane.opacityProperty().bind(Bindings.when(disabled).then(0.5).otherwise(1));
    }

    public PluginComponent(Project project, Path file, PluginComponentBlock block) {
        this(project, file);
        setBlock(block);
    }

    public PluginComponentBlock load() throws IOException, JSONException {
        if (!isLoaded()) {
            VisualBukkitApp.getLogger().info("Loading plugin component: " + getName());
            setBlock(BlockRegistry.newPluginComponent(new JSONObject(Files.readString(file))));
            block.updateState();
        }
        return block;
    }

    public void unload() throws IOException {
        if (isLoaded() && !project.isOpen(this)) {
            save();
            block = null;
        }
    }

    public void save() throws IOException {
        if (isLoaded()) {
            Files.createDirectories(file.getParent());
            Files.writeString(file, block.serialize().toString());
        }
    }

    public void delete() throws IOException {
        Files.deleteIfExists(file);
        block = null;
    }

    public boolean containsBlock(String blockUUID) {
        if (project.isOpen(this)) {
            return (VisualBukkitApp.getPrimaryStage().getScene().lookup("#" + blockUUID) instanceof Block b) && b.getPluginComponentBlock().equals(block);
        } else {
            try {
                return Files.readString(file, StandardCharsets.UTF_8).contains(blockUUID);
            } catch (IOException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to read file", e);
                return false;
            }
        }
    }

    public void setDisabled(boolean disabled) {
        this.disabled.set(disabled);
    }

    public void setBlock(PluginComponentBlock block) {
        this.block = block;
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

    public PluginComponentPane getPane() {
        return pane;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public String getName() {
        return file.getFileName().toString();
    }

    public Path getFile() {
        return file;
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
        return file.equals(that.file);
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
