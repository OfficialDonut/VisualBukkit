package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.WeakHashMap;

public class UndoManager {

    private static Map<BlockCanvas, Deque<RevertableAction>> undoQueues = new WeakHashMap<>();
    private static Map<BlockCanvas, Deque<RevertableAction>> redoQueues = new WeakHashMap<>();

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

    public static void run(RevertableAction action) {
        action.run();
        BlockCanvas canvas = getCurrentCanvas();
        if (canvas != null) {
            Deque<RevertableAction> undoActions = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            if (undoActions.size() == 25) {
                undoActions.removeLast();
            }
            undoActions.push(action);
        }
    }

    public static void undo() {
        BlockCanvas canvas = getCurrentCanvas();
        if (canvas != null) {
            Deque<RevertableAction> undoActions = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            Deque<RevertableAction> redoActions = redoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            if (!undoActions.isEmpty()) {
                if (redoActions.size() == 25) {
                    redoActions.removeLast();
                }
                RevertableAction action = undoActions.pop();
                redoActions.push(action);
                action.revert();
            }
        }
    }

    public static void redo() {
        BlockCanvas canvas = getCurrentCanvas();
        if (canvas != null) {
            Deque<RevertableAction> redoActions = redoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            Deque<RevertableAction> undoActions = undoQueues.computeIfAbsent(canvas, k -> new ArrayDeque<>(25));
            if (!redoActions.isEmpty()) {
                if (undoActions.size() == 25) {
                    undoActions.removeLast();
                }
                RevertableAction action = redoActions.pop();
                undoActions.push(action);
                action.run();
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

    public interface RevertableAction {

        void run();

        void revert();
    }

    public static RevertableAction EMPTY_ACTION = new RevertableAction() {

        @Override
        public void run() {}

        @Override
        public void revert() {}
    };
}
