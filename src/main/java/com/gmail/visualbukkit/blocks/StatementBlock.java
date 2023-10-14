package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.UndoManager;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public non-sealed abstract class StatementBlock extends Block {

    public StatementBlock() {
        getStyleClass().add("statement-block");

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
                getStatementHolder().showConnector(this, 2 * e.getY() > getHeight());
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            StatementBlock block = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.getCurrent().accept(block);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public abstract String generateJava();

    public UndoManager.RevertibleAction delete() {
        return getStatementHolder() != null ? getStatementHolder().remove(this) : UndoManager.RevertibleAction.NOP;
    }

    public StatementHolder getStatementHolder() {
        return (StatementHolder) getParent();
    }

    public static class Factory extends BlockFactory<StatementBlock> {

        public Factory(Class<?> clazz) {
            super(clazz);
        }

        @Override
        protected StatementBlock createUnknown() {
            return new StatementBlock.Unknown();
        }
    }

    @BlockDefinition(uid = "unknown-statement", name = "Unknown Statement")
    public static class Unknown extends StatementBlock {

        protected static final Factory factory = new Factory(Unknown.class);
        private JSONObject json;

        @Override
        public String generateJava() {
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
