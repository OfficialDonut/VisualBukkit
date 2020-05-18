package us.donut.visualbukkit.blocks;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.bukkit.configuration.file.YamlConfiguration;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.editor.ProjectManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class UndoManager {

    private static Map<BlockPane, Deque<YamlConfiguration>> undoDeques = new HashMap<>();
    private static Map<BlockPane, Deque<YamlConfiguration>> redoDeques = new HashMap<>();

    static {
        VisualBukkit.getInstance().getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isShortcutDown() && event.getCode() == KeyCode.Z) {
                if (event.isShiftDown()) {
                    redo();
                } else {
                    undo();
                }
            }
        });
    }

    public static void capture() {
        BlockPane blockPane = (BlockPane) ProjectManager.getCurrentProject().getTabPane().getSelectionModel().getSelectedItem();
        if (blockPane != null) {
            Deque<YamlConfiguration> undoStates = undoDeques.computeIfAbsent(blockPane, pane -> new ArrayDeque<>(25));
            if (undoStates.size() == 25) {
                undoStates.removeLast();
            }
            undoStates.push(getCurrentState(blockPane));
        }
    }

    public static void clear() {
        undoDeques.clear();
        redoDeques.clear();
    }

    public static void undo() {
        BlockPane blockPane = (BlockPane) ProjectManager.getCurrentProject().getTabPane().getSelectionModel().getSelectedItem();
        if (blockPane != null) {
            Deque<YamlConfiguration> undoStates = undoDeques.computeIfAbsent(blockPane, pane -> new ArrayDeque<>(25));
            Deque<YamlConfiguration> redoStates = redoDeques.computeIfAbsent(blockPane, pane -> new ArrayDeque<>(25));
            if (!undoStates.isEmpty()) {
                if (redoStates.size() == 25) {
                    redoStates.removeLast();
                }
                redoStates.push(getCurrentState(blockPane));
                blockPane.getBlockArea().getChildren().clear();
                try {
                    blockPane.load(undoStates.pop());
                } catch (Exception e) {
                    redo();
                    VisualBukkit.displayException("Failed to undo changes", e);
                }
            }
        }
    }

    public static void redo() {
        BlockPane blockPane = (BlockPane) ProjectManager.getCurrentProject().getTabPane().getSelectionModel().getSelectedItem();
        if (blockPane != null) {
            Deque<YamlConfiguration> redoStates = redoDeques.computeIfAbsent(blockPane, pane -> new ArrayDeque<>(25));
            Deque<YamlConfiguration> undoStates = undoDeques.computeIfAbsent(blockPane, pane -> new ArrayDeque<>(25));
            if (!redoStates.isEmpty()) {
                blockPane.getBlockArea().getChildren().clear();
                YamlConfiguration state = redoStates.pop();
                undoStates.push(state);
                try {
                    blockPane.load(state);
                } catch (Exception e) {
                    undo();
                    VisualBukkit.displayException("Failed to redo changes", e);
                }
            }
        }
    }

    private static YamlConfiguration getCurrentState(BlockPane blockPane) {
        YamlConfiguration state = new YamlConfiguration();
        blockPane.unload(state);
        state.set("open", false);
        return state;
    }
}
