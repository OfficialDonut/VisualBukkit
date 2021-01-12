package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.blocks.expressions.ExprSimpleVariable;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.UndoManager;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.WindowEvent;

public abstract class SimpleVariableBlock extends StatementBlock {

    public SimpleVariableBlock() {
        MenuItem copyVarItem = new MenuItem("Copy Variable");
        MenuItem pasteVarItem = new MenuItem("Paste Variable");
        copyVarItem.setOnAction(e -> {
            ExprSimpleVariable expr = new ExprSimpleVariable();
            expr.deserialize(serialize());
            CopyPasteManager.copy(expr);
        });
        pasteVarItem.setOnAction(e -> {
            if (!pasteVarItem.isDisable()) {
                UndoManager.capture();
                ExprSimpleVariable expr = (ExprSimpleVariable) CopyPasteManager.paste();
                ((ChoiceParameter) parameters.get(0)).setValue(expr.parameters.get(0).toJava());
                ((StringLiteralParameter) parameters.get(1)).setText(((StringLiteralParameter) expr.parameters.get(1)).getText());
            }
        });
        contextMenu.getItems().addAll(new SeparatorMenuItem(), copyVarItem, pasteVarItem);
        contextMenu.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> {
            BlockDefinition<?> copied = CopyPasteManager.peek();
            pasteVarItem.setDisable(copied == null || !ExprSimpleVariable.class.isAssignableFrom(copied.getBlockClass()));
        });
    }
}
