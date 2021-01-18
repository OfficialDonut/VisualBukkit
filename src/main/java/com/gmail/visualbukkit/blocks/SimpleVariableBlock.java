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
                ExprSimpleVariable expr = (ExprSimpleVariable) CopyPasteManager.paste();
                UndoManager.run(new UndoManager.RevertableAction() {
                    String arg0;
                    String arg1;
                    @Override
                    public void run() {
                        arg0 = arg(0);
                        arg1 = arg(1);
                        ((ChoiceParameter) parameters.get(0)).setValue(expr.parameters.get(0).toJava());
                        ((StringLiteralParameter) parameters.get(1)).setText(expr.parameters.get(1).toJava());
                    }
                    @Override
                    public void revert() {
                        ((ChoiceParameter) parameters.get(0)).setValue(arg0);
                        ((StringLiteralParameter) parameters.get(1)).setText(arg1);
                    }
                });
            }
        });
        contextMenu.getItems().addAll(new SeparatorMenuItem(), copyVarItem, pasteVarItem);
        contextMenu.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> {
            BlockDefinition<?> copied = CopyPasteManager.peek();
            pasteVarItem.setDisable(copied == null || !ExprSimpleVariable.class.isAssignableFrom(copied.getBlockClass()));
        });
    }
}
