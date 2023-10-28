package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.blocks.classes.ClassInfo;
import com.gmail.visualbukkit.ui.PopOverSelector;
import javafx.css.PseudoClass;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import org.json.JSONObject;

public class ExpressionParameter extends Region implements BlockParameter {

    private static final PseudoClass CONNECTING_STYLE_CLASS = PseudoClass.getPseudoClass("connecting");
    private static final PseudoClass DRAG_OVER_STYLE_CLASS = PseudoClass.getPseudoClass("drag-over");

    private final ClassInfo type;
    private final PopOverSelector<ExpressionBlock.Factory> expressionSelector;
    private ExpressionBlock expression;

    public ExpressionParameter(ClassInfo type) {
        this.type = type;
        expressionSelector = new PopOverSelector<>(BlockRegistry.getExpressions());
        expressionSelector.getStyleClass().add("expression-parameter");

        expressionSelector.setOnAction(e -> {
            ExpressionBlock.Factory factory = expressionSelector.getValue();
            if (factory != null) {
                UndoManager.execute(setExpression(factory.newBlock()));
                expression.requestFocus();
            }
        });

        expressionSelector.setOnDragOver(e -> {
            if (e.getGestureSource() instanceof ExpressionBlock) {
                expressionSelector.pseudoClassStateChanged(DRAG_OVER_STYLE_CLASS, true);
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        expressionSelector.setOnDragDropped(e -> {
            expressionSelector.pseudoClassStateChanged(DRAG_OVER_STYLE_CLASS, false);
            UndoManager.execute(setExpression((ExpressionBlock) e.getGestureSource()));
            e.setDropCompleted(true);
            e.consume();
        });

        expressionSelector.setOnDragExited(e -> {
            expressionSelector.pseudoClassStateChanged(DRAG_OVER_STYLE_CLASS, false);
            e.consume();
        });

        ExpressionBlock.DRAGGING_PROPERTY.addListener((observable, oldValue, newValue) -> expressionSelector.pseudoClassStateChanged(CONNECTING_STYLE_CLASS, newValue));
        getChildren().add(expressionSelector);
    }

    public UndoManager.RevertibleAction setExpression(ExpressionBlock block) {
        return new UndoManager.RevertibleAction() {
            private UndoManager.RevertibleAction deleteAction;
            @Override
            public void execute() {
                (deleteAction = block.delete()).execute();
                expression = block;
                getChildren().setAll(block);
                ProjectManager.getCurrentProject().updateBlockStates();
            }
            @Override
            public void revert() {
                deleteExpression().execute();
                deleteAction.revert();
                ProjectManager.getCurrentProject().updateBlockStates();
            }
        };
    }

    public UndoManager.RevertibleAction deleteExpression() {
        return new UndoManager.RevertibleAction() {
            private ExpressionBlock oldExpression;
            @Override
            public void execute() {
                oldExpression = expression;
                expression = null;
                expressionSelector.setValue(null);
                getChildren().setAll(expressionSelector);
                ProjectManager.getCurrentProject().updateBlockStates();
            }
            @Override
            public void revert() {
                setExpression(oldExpression).execute();
                ProjectManager.getCurrentProject().updateBlockStates();
            }
        };
    }

    @Override
    public void updateState() {
        if (expression != null) {
            expression.updateState();
        }
    }

    @Override
    public String generateJava() {
        return expression != null ?
                ClassInfo.convert(expression.getReturnType(), type, expression.generateJava()) :
                ClassInfo.convert(ClassInfo.of(Object.class), type, "((Object) null)");
    }

    @Override
    public Object serialize() {
        return expression != null ? expression.serialize() : null;
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof JSONObject json) {
            setExpression(BlockRegistry.getExpression(json.optString("uid")).newBlock(json)).execute();
        }
    }
}
