package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.ui.*;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public non-sealed abstract class Expression extends BlockDefinition {

    public Expression(String id, String title, String tag, String description) {
        super(id, title, tag, description);
    }

    public abstract ClassInfo getReturnType();

    @Override
    public ExpressionSource createSource() {
        return new ExpressionSource(this);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    @Override
    public int compareTo(BlockDefinition obj) {
        return obj instanceof PluginComponent || obj instanceof Statement ? 1 : super.compareTo(obj);
    }

    @Override
    public String toString() {
        return super.toString() + " → (" + getReturnType() + ")";
    }

    public static non-sealed abstract class Block extends BlockNode {

        private static PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");

        public Block(Expression expression, BlockParameter<?>... parameters) {
            super(expression, parameters);

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
                    PluginComponent.Block block = ProjectManager.getCurrentProject().getCurrentPluginComponent();
                    if (block != null) {
                        block.toggleExpressionParameters(true);
                    }
                    e.consume();
                }
            });

            setOnDragDone(e -> {
                setDisable(false);
                PluginComponent.Block block = ProjectManager.getCurrentProject().getCurrentPluginComponent();
                if (block != null) {
                    block.toggleExpressionParameters(false);
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
                        (((ExpressionParameter) combineStringsBlock.parameters[0]).setExpression(Block.this)).run();
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
            pseudoClassStateChanged(NESTED_STYLE_CLASS, i % 2 == 1);
        }

        public abstract String toJava();

        public ExpressionParameter getExpressionParameter() {
            return getParent() != null ? (ExpressionParameter) getParent().getParent() : null;
        }

        @Override
        public Expression getDefinition() {
            return (Expression) super.getDefinition();
        }
    }
}
