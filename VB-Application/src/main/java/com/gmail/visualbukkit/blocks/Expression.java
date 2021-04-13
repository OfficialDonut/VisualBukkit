package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public abstract class Expression extends BlockDefinition<Expression.Block> {

    private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");

    private final ClassInfo returnType;

    public Expression(String id, ClassInfo returnType) {
        super(id);
        this.returnType = returnType;
    }

    public final ClassInfo getReturnType() {
        return returnType;
    }

    public static abstract class Block extends CodeBlock<Expression> {

        public Block(Expression expression, BlockParameter... parameters) {
            super(expression);

            addToHeader(new Label(expression.getTitle()));
            addParameterLines(parameters);
            getSyntaxBox().getStyleClass().add("expression-block");

            MenuItem addStringItem = new MenuItem(VisualBukkitApp.getString("context_menu.add_string"));
            addStringItem.setOnAction(e -> {
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

            MenuItem copyItem = new MenuItem(VisualBukkitApp.getString("context_menu.copy"));
            MenuItem cutItem = new MenuItem(VisualBukkitApp.getString("context_menu.cut"));
            MenuItem deleteItem = new MenuItem(VisualBukkitApp.getString("context_menu.delete"));
            copyItem.setOnAction(e -> copy());
            cutItem.setOnAction(e -> UndoManager.run(cut()));
            deleteItem.setOnAction(e -> UndoManager.run(delete()));
            getContextMenu().getItems().addAll(copyItem, cutItem, deleteItem, addStringItem);
            getContextMenu().setOnShowing(e -> addStringItem.setVisible(getExpressionParameter().getType().getClazz() == String.class || getDefinition().returnType.getClazz() == String.class));

            if (getDefinition().getReturnType().getClazz() == boolean.class) {
                MenuItem negateBooleanItem = new MenuItem(VisualBukkitApp.getString("context_menu.negate_boolean"));
                getContextMenu().getItems().add(negateBooleanItem);
                negateBooleanItem.setOnAction(e -> {
                    ExpressionParameter exprParameter = getExpressionParameter();
                    UndoManager.run(new UndoManager.RevertableAction() {
                        @Override
                        public void run() {
                            Block negateBooleanBlock = BlockRegistry.getExpression("expr-negate-boolean").createBlock();
                            exprParameter.setExpression(negateBooleanBlock).run();
                            (((ExpressionParameter) negateBooleanBlock.getParameters().get(0)).setExpression(Block.this)).run();
                        }

                        @Override
                        public void revert() {
                            exprParameter.setExpression(Block.this).run();
                        }
                    });
                });
            }

            getSyntaxBox().setOnDragDetected(e -> {
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
        }

        @Override
        protected void handleSelectedAction(KeyEvent e) {
            KeyCode key = e.getCode();
            if (e.isShortcutDown()) {
                if (key == KeyCode.C) {
                    copy();
                } else if (key == KeyCode.X) {
                    UndoManager.run(cut());
                }
            } else if (key == KeyCode.DELETE) {
                UndoManager.run(delete());
            }
        }

        public void copy() {
            CopyPasteManager.copyExpression(this);
        }

        public UndoManager.RevertableAction cut() {
            copy();
            return delete();
        }

        public UndoManager.RevertableAction delete() {
            return getExpressionParameter().clear();
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
            getSyntaxBox().pseudoClassStateChanged(NESTED_STYLE_CLASS, i % 2 == 1);
        }

        public abstract String toJava();

        public ExpressionParameter getExpressionParameter() {
            return (ExpressionParameter) getParent();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " → (" + returnType.getDisplayClassName() + ")";
    }
}
