package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

public non-sealed abstract class StatementBlock extends Block {

    public StatementBlock() {
        getStyleClass().add("statement-block");

        ActionMenuItem copyItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.copy"), e -> CopyPasteManager.copyStatement(this, false));
        ActionMenuItem cutItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.cut"), e -> {
            CopyPasteManager.copyStatement(this, false);
            UndoManager.current().execute(this::delete);
        });
        ActionMenuItem deleteItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete"), e -> UndoManager.current().execute(this::delete));
        ActionMenuItem copyStackItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.copy_stack"), e -> CopyPasteManager.copyStatement(this, true));
        ActionMenuItem cutStackItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.cut_stack"), e -> {
            CopyPasteManager.copyStatement(this, true);
            UndoManager.current().execute(() -> getParentStatementHolder().removeStack(this));
        });
        ActionMenuItem deleteStackItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete_stack"), e -> UndoManager.current().execute(() -> getParentStatementHolder().removeStack(this)));
        ActionMenuItem pasteBeforeItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste_before"), e -> UndoManager.current().execute(() -> getParentStatementHolder().addBefore(this, CopyPasteManager.pasteStatement())));
        ActionMenuItem pasteAfterItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste_after"), e -> UndoManager.current().execute(() -> getParentStatementHolder().addAfter(this, CopyPasteManager.pasteStatement())));
        pasteBeforeItem.disableProperty().bind(CopyPasteManager.statementCopiedProperty().not());
        pasteAfterItem.disableProperty().bind(pasteBeforeItem.disableProperty());
        getContextMenu().getItems().addAll(copyItem, cutItem, deleteItem, new SeparatorMenuItem(), copyStackItem, cutStackItem, deleteStackItem, new SeparatorMenuItem(), pasteBeforeItem, pasteAfterItem);

        setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                Image image = snapshot(snapshotParameters, new WritableImage((int) Math.min(getWidth(), 500), (int) Math.min(getHeight(), 500)));
                dragboard.setDragView(image);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                setVisible(false);
                setManaged(false);
                e.consume();
            }
        });

        setOnDragDone(e -> {
            StatementConnector.hideCurrent();
            setVisible(true);
            setManaged(true);
            e.consume();
        });

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                getParentStatementHolder().showConnector(this, 2 * e.getY() > getHeight());
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            StatementBlock block = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.current().accept(block);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public String generateDebugJava(BuildInfo buildInfo) {
        String exceptionVar = RandomStringUtils.randomAlphanumeric(16);
        String reportMethod = "Class.forName(\"com.gmail.visualbukkit.plugin.VisualBukkitPlugin\").getDeclaredMethod(\"reportException\", String.class, Throwable.class)";
        return "try { %s } catch (Exception $%s) { %s.invoke(null, \"%s\", $%s); }".formatted(generateJava(buildInfo), exceptionVar, reportMethod, getUUID(), exceptionVar);
    }

    public abstract String generateJava(BuildInfo buildInfo);

    @Override
    public void delete() {
        if (getParentStatementHolder() != null) {
            getParentStatementHolder().remove(this);
        }
    }

    public StatementHolder getParentStatementHolder() {
        return (StatementHolder) getParent();
    }

    @BlockDefinition(id = "unknown-statement", name = "Unknown Statement")
    public static class Unknown extends StatementBlock {

        private JSONObject json;

        @Override
        public void updateState() {
            super.updateState();
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }

        @Override
        public String generateJava(BuildInfo buildInfo) {
            return "";
        }

        @Override
        public JSONObject serialize() {
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            this.json = json;
        }
    }
}
