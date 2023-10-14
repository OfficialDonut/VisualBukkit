package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;

import java.util.ArrayDeque;
import java.util.Deque;

public class UndoManager {

    private static final int CAPACITY = 10;
    private static final Deque<RevertibleAction> undoQueue = new ArrayDeque<>(CAPACITY);
    private static final Deque<RevertibleAction> redoQueue = new ArrayDeque<>(CAPACITY);

    public static void execute(RevertibleAction action) {
        action.execute();
        if (undoQueue.size() == CAPACITY) {
            undoQueue.removeLast();
        }
        undoQueue.addFirst(action);
    }

    public static void undo() {
        if (undoQueue.isEmpty()) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.undo_failure"));
            return;
        }
        RevertibleAction action = undoQueue.removeFirst();
        action.revert();
        redoQueue.addFirst(action);
    }

    public static void redo() {
        if (redoQueue.isEmpty()) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.redo_failure"));
            return;
        }
        RevertibleAction action = redoQueue.removeFirst();
        action.execute();
        undoQueue.addFirst(action);
    }

    public static void clear() {
        undoQueue.clear();
        redoQueue.clear();
    }

    public interface RevertibleAction {

        void execute();

        void revert();

        RevertibleAction NOP = new RevertibleAction() {
            @Override
            public void execute() {}
            @Override
            public void revert() {}
        };
    }
}
