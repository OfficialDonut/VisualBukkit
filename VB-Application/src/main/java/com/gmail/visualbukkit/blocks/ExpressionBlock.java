package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
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

    private static final PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");
    public static final SimpleBooleanProperty DRAGGING_PROPERTY = new SimpleBooleanProperty();

    public ExpressionBlock() {
        getStyleClass().add("expression-block");

        getContextMenu().getItems().addAll(
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.copy"), e -> CopyPasteManager.copyExpression(this)),
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.cut"), e -> {
                    CopyPasteManager.copyExpression(this);
                    UndoManager.current().execute(this::delete);
                }),
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete"), e -> UndoManager.current().execute(this::delete)));

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

    @Override
    public void updateState() {
        super.updateState();
        int level = 0;
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof ExpressionBlock) {
                level++;
            }
            parent = parent.getParent();
        }
        pseudoClassStateChanged(NESTED_STYLE_CLASS, level % 2 == 1);
    }

    public abstract String generateJava(BuildInfo buildInfo);

    public abstract ClassInfo getReturnType();

    @Override
    public void delete() {
        if (getExpressionParameter() != null) {
            getExpressionParameter().deleteExpression();
        }
    }

    public ExpressionParameter getExpressionParameter() {
        return (ExpressionParameter) getParent();
    }

    @BlockDefinition(id = "unknown-expression", name = "Unknown Expression")
    public static class Unknown extends ExpressionBlock {

        private JSONObject json;

        @Override
        public void updateState() {
            super.updateState();
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }

        @Override
        public String generateJava(BuildInfo buildInfo) {
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
