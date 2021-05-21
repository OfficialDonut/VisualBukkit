package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.LanguageManager;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.WindowEvent;

public class StatSetComplexVariable extends Statement {

    public StatSetComplexVariable() {
        super("stat-set-complex-variable");
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this, new ChoiceParameter("global", "persistent"), new StringLiteralParameter(), new ExpressionParameter(ClassInfo.OBJECT), new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.VARIABLES);
            }

            @Override
            public String toJava() {
                return "VariableManager.setVariable(" + arg(0).equals("persistent") + "," + arg(3) + "," + arg(1) + "," + arg(2) + ");";
            }
        };

        ActionMenuItem pasteVariableItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_variable"), e -> ExprComplexVariable.paste(block));
        block.getContextMenu().getItems().addAll(new SeparatorMenuItem(), new ActionMenuItem(LanguageManager.get("context_menu.copy_variable"), e -> ExprComplexVariable.copy(block)), pasteVariableItem);
        block.getContextMenu().addEventHandler(WindowEvent.WINDOW_SHOWING, e -> pasteVariableItem.setDisable(!ExprComplexVariable.isVariableCopied()));

        return block;
    }
}
