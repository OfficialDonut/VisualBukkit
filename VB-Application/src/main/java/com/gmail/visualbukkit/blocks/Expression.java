package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.CopyPasteManager;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.UndoManager;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public abstract class Expression extends BlockDefinition {

    public Expression(String id) {
        super(id);
    }

    public abstract ClassInfo getReturnType();

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    @Override
    public String toString() {
        return super.toString() + " → (" + getReturnType() + ")";
    }

    public static abstract class Block extends CodeBlock {

        private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");

        public Block(Expression expression, BlockParameter... parameters) {
            super(expression);

            getBody().getStyleClass().add("expression-block");
            addParameterLines(expression.getParameterNames(), parameters);

            getBody().setOnDragDetected(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                    SnapshotParameters snapshotParameters = new SnapshotParameters();
                    snapshotParameters.setFill(Color.TRANSPARENT);
                    Image image = snapshot(snapshotParameters, new WritableImage((int) Math.min(getWidth(), 500), (int) Math.min(getHeight(), 500)));
                    dragboard.setDragView(image, -1, -1);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("");
                    dragboard.setContent(content);
                }
                e.consume();
            });

            ActionMenuItem addStringItem = new ActionMenuItem("Add String", e -> {
                ExpressionParameter exprParameter = getExpressionParameter();
                UndoManager.run(new UndoManager.RevertableAction() {
                    @Override
                    public void run() {
                        Block combineStringsBlock = BlockRegistry.getExpression("expr-combine-strings").createBlock();
                        exprParameter.setExpression(combineStringsBlock).run();
                        (((ExpressionParameter) combineStringsBlock.getParameters().get(0)).setExpression(Block.this)).run();
                    }
                    @Override
                    public void revert() {
                        exprParameter.setExpression(Block.this).run();
                    }
                });
            });

            getContextMenu().getItems().addAll(
                    new ActionMenuItem(LanguageManager.get("context_menu.copy"), e -> CopyPasteManager.copyExpression(this)),
                    new ActionMenuItem(LanguageManager.get("context_menu.cut"), e -> {
                        CopyPasteManager.copyExpression(this);
                        UndoManager.run(getExpressionParameter().clear());
                    }),
                    new ActionMenuItem(LanguageManager.get("context_menu.delete"), e -> UndoManager.run(getExpressionParameter().clear())),
                    addStringItem);

            getContextMenu().setOnShowing(e -> addStringItem.setVisible(getExpressionParameter().getType().getClassType() == String.class || getDefinition().getReturnType().getClassType() == String.class));
        }

        @Override
        public void handleSelectedAction(KeyEvent e) {
            if (e.isShortcutDown()) {
                if (e.getCode() == KeyCode.C) {
                    CopyPasteManager.copyExpression(this);
                } else if (e.getCode() == KeyCode.X) {
                    CopyPasteManager.copyExpression(this);
                    UndoManager.run(getExpressionParameter().clear());
                }
            } else if (e.getCode() == KeyCode.DELETE) {
                UndoManager.run(getExpressionParameter().clear());
            }
        }

        @Override
        public void update() {
            super.update();
            int i = 0;
            Parent parent = getParent();
            while (parent != null) {
                if (parent instanceof Expression.Block) {
                    i++;
                }
                parent = parent.getParent();
            }
            getBody().pseudoClassStateChanged(NESTED_STYLE_CLASS, i % 2 == 1);
        }

        public abstract String toJava();

        public ExpressionParameter getExpressionParameter() {
            return (ExpressionParameter) getParent();
        }

        @Override
        public Expression getDefinition() {
            return (Expression) super.getDefinition();
        }
    }
}
