package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayDeque;
import java.util.Deque;

public class UndoManager {

    private static final int CAPACITY = 25;
    private static Deque<RevertableAction> undoQueue = new ArrayDeque<>(CAPACITY);
    private static Deque<RevertableAction> redoQueue = new ArrayDeque<>(CAPACITY);

    static {
        VisualBukkitApp.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown() && e.getCode() == KeyCode.Z && !(VisualBukkitApp.getScene().getFocusOwner() instanceof TextField)) {
                if (e.isShiftDown()) {
                    redo();
                } else {
                    undo();
                }
            }
        });
    }

    public static void run(RevertableAction action) {
        if (action == EMPTY_ACTION) {
            return;
        }
        action.run();
        if (undoQueue.size() == CAPACITY) {
            undoQueue.removeLast();
        }
        undoQueue.push(action);
    }

    public static void undo() {
        if (!undoQueue.isEmpty()) {
            if (redoQueue.size() == CAPACITY) {
                redoQueue.removeLast();
            }
            RevertableAction action = undoQueue.pop();
            action.revert();
            redoQueue.push(action);
        }
    }

    public static void redo() {
        if (!redoQueue.isEmpty()) {
            if (undoQueue.size() == CAPACITY) {
                undoQueue.removeLast();
            }
            RevertableAction action = redoQueue.pop();
            action.run();
            undoQueue.push(action);
        }
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
