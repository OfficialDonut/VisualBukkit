package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.PluginComponentPane;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.Deque;

public class UndoManager {

    private static final int CAPACITY = 10;
    private final Deque<JSONObject> undoQueue = new ArrayDeque<>(CAPACITY);
    private final Deque<JSONObject> redoQueue = new ArrayDeque<>(CAPACITY);
    private final PluginComponentPane pluginComponent;

    public UndoManager(PluginComponentPane pluginComponent) {
        this.pluginComponent = pluginComponent;
    }

    public void execute(Runnable runnable) {
        captureState();
        runnable.run();
        pluginComponent.getBlock().updateState();
    }

    public void captureState() {
        if (undoQueue.size() == CAPACITY) {
            undoQueue.removeLast();
        }
        undoQueue.addFirst(pluginComponent.getBlock().serialize());
    }

    public void undo() {
        restore(undoQueue, redoQueue, VisualBukkitApp.localizedText("notification.undo_failure"));
    }

    public void redo() {
        restore(redoQueue, undoQueue, VisualBukkitApp.localizedText("notification.redo_failure"));
    }

    private void restore(Deque<JSONObject> queue, Deque<JSONObject> backupQueue, String errorMessage) {
        if (queue.isEmpty()) {
            VisualBukkitApp.displayError(errorMessage);
            return;
        }
        backupQueue.addFirst(pluginComponent.getBlock().serialize());
        pluginComponent.setBlock(BlockRegistry.newPluginComponent(queue.removeFirst()));
        pluginComponent.getBlock().updateState();
    }

    public static UndoManager current() {
        Project project = ProjectManager.current();
        return project != null && project.getOpenPluginComponent() != null ? project.getOpenPluginComponent().getUndoManager() : defaultUndoManager;
    }

    private static final UndoManager defaultUndoManager = new UndoManager(null) {
        @Override
        public void captureState() {}
    };
}
