package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.CodeBlock;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.UndoManager;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.WindowEvent;
import org.json.JSONObject;

public class ExprComplexVariable extends Expression {

    private static String copiedType;
    private static String copiedName;
    private static JSONObject copiedArgument;

    public ExprComplexVariable() {
        super("expr-complex-variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    protected static void copy(CodeBlock block) {
        copiedType = ((ChoiceParameter) block.getParameters().get(0)).getValue();
        copiedName = ((StringLiteralParameter) block.getParameters().get(1)).getText();
        copiedArgument = ((ExpressionParameter) block.getParameters().get(2)).serialize();
    }

    protected static void paste(CodeBlock block) {
        String oldType = ((ChoiceParameter) block.getParameters().get(0)).getValue();
        String oldName = ((StringLiteralParameter) block.getParameters().get(1)).getText();
        UndoManager.RevertableAction setExpressionAction = ((ExpressionParameter) block.getParameters().get(2)).setExpression(copiedArgument != null ? BlockRegistry.getExpression(copiedArgument.getString("=")).createBlock(copiedArgument) : null);
        UndoManager.run(new UndoManager.RevertableAction() {
            @Override
            public void run() {
                ((ChoiceParameter) block.getParameters().get(0)).setValue(copiedType);
                ((StringLiteralParameter) block.getParameters().get(1)).setText(copiedName);
                setExpressionAction.run();
            }
            @Override
            public void revert() {
                ((ChoiceParameter) block.getParameters().get(0)).setValue(oldType);
                ((StringLiteralParameter) block.getParameters().get(1)).setText(oldName);
                setExpressionAction.revert();
            }
        });
    }

    protected static boolean isVariableCopied() {
        return copiedType != null;
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this, new ChoiceParameter("global", "persistent"), new StringLiteralParameter(), new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.VARIABLES);
            }

            @Override
            public String toJava() {
                return "VariableManager.getVariable(" + arg(0).equals("persistent") + "," + arg(1) + "," + arg(2) + ")";
            }
        };

        ActionMenuItem pasteVariableItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_variable"), e -> paste(block));
        block.getContextMenu().getItems().addAll(new SeparatorMenuItem(), new ActionMenuItem(LanguageManager.get("context_menu.copy_variable"), e -> copy(block)), pasteVariableItem);
        block.getContextMenu().addEventHandler(WindowEvent.WINDOW_SHOWING, e -> pasteVariableItem.setDisable(!isVariableCopied()));

        return block;
    }
}
