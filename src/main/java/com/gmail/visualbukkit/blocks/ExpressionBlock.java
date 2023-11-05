package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public non-sealed abstract class ExpressionBlock extends Block {

    public static final SimpleBooleanProperty DRAGGING_PROPERTY = new SimpleBooleanProperty();

    public ExpressionBlock() {
        getStyleClass().add("expression-block");

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
                setDisable(true);
                DRAGGING_PROPERTY.set(true);
                e.consume();
            }
        });

        setOnDragDone(e -> {
            setDisable(false);
            DRAGGING_PROPERTY.set(false);
            e.consume();
        });
    }

    public abstract String generateJava();

    public abstract ClassInfo getReturnType();

    public UndoManager.RevertibleAction delete() {
        return getExpressionParameter() != null ? getExpressionParameter().deleteExpression() : UndoManager.RevertibleAction.NOP;
    }

    public ExpressionParameter getExpressionParameter() {
        return (ExpressionParameter) getParent();
    }

    public static class Factory extends BlockFactory<ExpressionBlock> {

        public Factory(Class<?> clazz) {
            super(clazz);
        }

        @Override
        protected ExpressionBlock createUnknown() {
            return new ExpressionBlock.Unknown();
        }
    }

    @BlockDefinition(uid = "unknown-expression", name = "Unknown Expression")
    public static class Unknown extends ExpressionBlock {

        protected static final Factory factory = new Factory(Unknown.class);
        private JSONObject json;

        @Override
        public String generateJava() {
            return "((Object) null)";
        }

        @Override
        public ClassInfo getReturnType() {
            return ClassInfo.of(Object.class);
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
