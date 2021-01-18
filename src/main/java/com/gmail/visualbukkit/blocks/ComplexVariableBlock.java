package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;
import com.gmail.visualbukkit.blocks.expressions.ExprComplexVariable;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.UndoManager;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.WindowEvent;

public abstract class ComplexVariableBlock extends StatementBlock {

    public ComplexVariableBlock() {
        MenuItem copyVarItem = new MenuItem("Copy Variable");
        MenuItem pasteVarItem = new MenuItem("Paste Variable");
        copyVarItem.setOnAction(e -> {
            ExprComplexVariable expr = new ExprComplexVariable();
            expr.deserialize(serialize());
            CopyPasteManager.copy(expr);
        });
        pasteVarItem.setOnAction(e -> {
            if (!pasteVarItem.isDisable()) {
                ExprComplexVariable expr = (ExprComplexVariable) CopyPasteManager.paste();
                UndoManager.run(new UndoManager.RevertableAction() {
                    String arg0;
                    ExpressionBlock<?> arg1;
                    @Override
                    public void run() {
                        arg0 = arg(0);
                        arg1 = ((ExpressionParameter) parameters.get(1)).getExpression();
                        ((ChoiceParameter) parameters.get(0)).setValue(expr.parameters.get(0).toJava());
                        ((ExpressionParameter) parameters.get(1)).setExpression(((ExpressionParameter) expr.parameters.get(1)).getExpression());
                    }
                    @Override
                    public void revert() {
                        ((ChoiceParameter) parameters.get(0)).setValue(arg0);
                        ((ExpressionParameter) parameters.get(1)).setExpression(arg1);
                    }
                });
            }
        });
        contextMenu.getItems().addAll(new SeparatorMenuItem(), copyVarItem, pasteVarItem);
        contextMenu.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> {
            BlockDefinition<?> copied = CopyPasteManager.peek();
            pasteVarItem.setDisable(copied == null || !ExprComplexVariable.class.isAssignableFrom(copied.getBlockClass()));
        });
    }
}
