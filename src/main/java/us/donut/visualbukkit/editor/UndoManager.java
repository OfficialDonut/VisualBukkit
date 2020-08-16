package us.donut.visualbukkit.editor;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.util.DataConfig;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class UndoManager {

    private static Map<BlockCanvas, Deque<DataConfig>> undoQueues = new HashMap<>();
    private static Map<BlockCanvas, Deque<DataConfig>> redoQueues = new HashMap<>();

    static {
        VisualBukkit.getInstance().getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown() && e.getCode() == KeyCode.Z) {
                if (e.isShiftDown()) {
                    redo();
                } else {
                    undo();
                }
            }
        });
    }

    public static void capture() {
        Project project = ProjectManager.getCurrentProject();
        if (project != null) {
            BlockCanvas canvas = project.getCurrentCanvas();
            if (canvas != null) {
                Deque<DataConfig> undoStates = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
                if (undoStates.size() == 25) {
                    undoStates.removeLast();
                }
                undoStates.push(getCurrentState(canvas));
            }
        }
    }

    public static void undo() {
        Project project = ProjectManager.getCurrentProject();
        if (project != null) {
            BlockCanvas canvas = project.getCurrentCanvas();
            if (canvas != null) {
                Deque<DataConfig> undoStates = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
                Deque<DataConfig> redoStates = redoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
                if (!undoStates.isEmpty()) {
                    if (redoStates.size() == 25) {
                        redoStates.removeLast();
                    }
                    redoStates.push(getCurrentState(canvas));
                    canvas.clear();
                    canvas.loadFrom(undoStates.pop());
                }
            }
        }
    }

    public static void redo() {
        Project project = ProjectManager.getCurrentProject();
        if (project != null) {
            BlockCanvas canvas = project.getCurrentCanvas();
            if (canvas != null) {
                Deque<DataConfig> redoStates = redoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
                Deque<DataConfig> undoStates = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
                if (!redoStates.isEmpty()) {
                    DataConfig state = redoStates.pop();
                    undoStates.push(state);
                    canvas.clear();
                    canvas.loadFrom(state);
                }
            }
        }
    }

    private static DataConfig getCurrentState(BlockCanvas canvas) {
        DataConfig config = new DataConfig();
        canvas.saveTo(config);
        return config;
    }
}
