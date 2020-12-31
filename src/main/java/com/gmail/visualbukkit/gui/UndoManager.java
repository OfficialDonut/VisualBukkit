package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.json.JSONObject;

import java.util.*;

public class UndoManager {

    private static Map<BlockCanvas, Deque<JSONObject>> undoQueues = new WeakHashMap<>();
    private static Map<BlockCanvas, Deque<JSONObject>> redoQueues = new WeakHashMap<>();

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
        BlockCanvas canvas = getCurrentCanvas();
        if (canvas != null) {
            Deque<JSONObject> undoStates = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            if (undoStates.size() == 25) {
                undoStates.removeLast();
            }
            undoStates.push(canvas.serialize());
        }
    }

    public static void undo() {
        BlockCanvas canvas = getCurrentCanvas();
        if (canvas != null) {
            Deque<JSONObject> undoStates = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            Deque<JSONObject> redoStates = redoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            if (!undoStates.isEmpty()) {
                if (redoStates.size() == 25) {
                    redoStates.removeLast();
                }
                redoStates.push(canvas.serialize());
                canvas.clear();
                canvas.deserialize(undoStates.pop());
            }
        }
    }

    public static void redo() {
        BlockCanvas canvas = getCurrentCanvas();
        if (canvas != null) {
            Deque<JSONObject> redoStates = redoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            Deque<JSONObject> undoStates = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            if (!redoStates.isEmpty()) {
                if (undoStates.size() == 25) {
                    undoStates.removeLast();
                }
                undoStates.push(canvas.serialize());
                canvas.clear();
                canvas.deserialize(redoStates.pop());
            }
        }
    }

    private static BlockCanvas getCurrentCanvas() {
        Tab tab = VisualBukkit.getInstance().getCanvasPane().getSelectionModel().getSelectedItem();
        if (tab != null) {
            Node content = tab.getContent();
            return content instanceof BlockCanvas ? (BlockCanvas) content : null;
        }
        return null;
    }
}
