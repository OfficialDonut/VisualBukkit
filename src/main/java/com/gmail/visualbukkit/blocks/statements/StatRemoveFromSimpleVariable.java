package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Description("Removes a value from a simple variable (the variable must be set to a number or a list)")
public class StatRemoveFromSimpleVariable extends StatementBlock {

    public StatRemoveFromSimpleVariable() {
        init("remove ", Object.class, " from ", new ChoiceParameter("local", "global", "persistent"), " ", new StringLiteralParameter());
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return "VariableManager.removeFromVariable(VariableType." + arg(1).toUpperCase() + "," + arg(0) + "," + arg(2) + ");";
    }
}
