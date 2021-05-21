package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.Project;
import com.gmail.visualbukkit.project.ProjectManager;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.WeakHashMap;

public class UndoManager {

    private static Map<Tab, Deque<RevertableAction>> undoQueues = new WeakHashMap<>();
    private static Map<Tab, Deque<RevertableAction>> redoQueues = new WeakHashMap<>();

    public static RevertableAction EMPTY_ACTION = new RevertableAction() {
        @Override
        public void run() {}
        @Override
        public void revert() {}
    };

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
        Tab tab = getOpenTab();
        if (tab != null) {
            Deque<RevertableAction> undoActions = undoQueues.computeIfAbsent(tab, k -> new ArrayDeque<>(25));
            if (undoActions.size() == 25) {
                undoActions.removeLast();
            }
            undoActions.push(action);
        }
    }

    public static void undo() {
        Tab tab = getOpenTab();
        if (tab != null) {
            Deque<RevertableAction> undoActions = undoQueues.computeIfAbsent(tab, k -> new ArrayDeque<>(25));
            Deque<RevertableAction> redoActions = redoQueues.computeIfAbsent(tab, k -> new ArrayDeque<>(25));
            if (!undoActions.isEmpty()) {
                if (redoActions.size() == 25) {
                    redoActions.removeLast();
                }
                RevertableAction action = undoActions.pop();
                action.revert();
                redoActions.push(action);
            }
        }
    }

    public static void redo() {
        Tab tab = getOpenTab();
        if (tab != null) {
            Deque<RevertableAction> redoActions = redoQueues.computeIfAbsent(tab, k -> new ArrayDeque<>(25));
            Deque<RevertableAction> undoActions = undoQueues.computeIfAbsent(tab, k -> new ArrayDeque<>(25));
            if (!redoActions.isEmpty()) {
                if (undoActions.size() == 25) {
                    undoActions.removeLast();
                }
                RevertableAction action = redoActions.pop();
                action.run();
                undoActions.push(action);
            }
        }
    }

    private static Tab getOpenTab() {
        Project project = ProjectManager.getCurrentProject();
        return project != null ? project.getPluginComponentPane().getSelectionModel().getSelectedItem() : null;
    }

    public interface RevertableAction {

        void run();

        void revert();
    }
}
