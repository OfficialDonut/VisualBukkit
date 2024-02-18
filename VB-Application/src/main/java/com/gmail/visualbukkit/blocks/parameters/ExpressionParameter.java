package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.ExpressionSelector;
import com.gmail.visualbukkit.blocks.definitions.core.*;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.css.PseudoClass;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import org.json.JSONObject;

public class ExpressionParameter extends Region implements BlockParameter {

    private static final PseudoClass CONNECTING_STYLE_CLASS = PseudoClass.getPseudoClass("connecting");
    private static final PseudoClass DRAG_OVER_STYLE_CLASS = PseudoClass.getPseudoClass("drag-over");

    private final ClassInfo type;
    private final ExpressionSelector expressionSelector;
    private ExpressionBlock expression;

    public ExpressionParameter(ClassInfo type) {
        this.type = type;
        expressionSelector = new ExpressionSelector(type);

        expressionSelector.setSelectAction(factory -> {
            UndoManager.current().execute(() -> setExpression(factory.newBlock()));
            expression.requestFocus();
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
            UndoManager.current().execute(() -> setExpression((ExpressionBlock) e.getGestureSource()));
            e.setDropCompleted(true);
            e.consume();
        });

        expressionSelector.setOnDragExited(e -> {
            expressionSelector.pseudoClassStateChanged(DRAG_OVER_STYLE_CLASS, false);
            e.consume();
        });

        ActionMenuItem pasteItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste"), e -> UndoManager.current().execute(() -> setExpression(CopyPasteManager.pasteExpression())));
        pasteItem.disableProperty().bind(CopyPasteManager.expressionCopiedProperty().not());
        expressionSelector.setContextMenu(new ContextMenu(
                new Menu(VisualBukkitApp.localizedText("menu.insert"), null,
                    new ActionMenuItem("Variable", e -> UndoManager.current().execute(() -> setExpression(new ExprLocalVariable()))),
                    new ActionMenuItem("Method", e -> UndoManager.current().execute(() -> setExpression(new ExprMethod()))),
                    new ActionMenuItem("String", e -> UndoManager.current().execute(() -> setExpression(new ExprString()))),
                    new ActionMenuItem("Number", e -> UndoManager.current().execute(() -> setExpression(new ExprNumber()))),
                    new ActionMenuItem("List", e -> UndoManager.current().execute(() -> setExpression(new ExprList())))),
                pasteItem));

        ExpressionBlock.DRAGGING_PROPERTY.addListener((observable, oldValue, newValue) -> expressionSelector.pseudoClassStateChanged(CONNECTING_STYLE_CLASS, newValue));
        getChildren().add(expressionSelector);
    }

    public void setExpression(ExpressionBlock block) {
        expression = block;
        block.delete();
        getChildren().setAll(block);
    }

    public void deleteExpression() {
        expression = null;
        expressionSelector.setValue(null);
        getChildren().setAll(expressionSelector);
    }

    @Override
    public void updateState() {
        if (expression != null) {
            expression.updateState();
        }
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return expression != null ?
                ClassInfo.convert(expression.getReturnType(), type, expression.generateJava(buildInfo)) :
                ClassInfo.convert(ClassInfo.of(Object.class), type, "((Object) null)");
    }

    @Override
    public Object serialize() {
        return expression != null ? expression.serialize() : null;
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof JSONObject json) {
            setExpression(BlockRegistry.newExpression(json));
        }
    }
}
