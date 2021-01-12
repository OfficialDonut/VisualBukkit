package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.SimpleVariableBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Category(Category.VARIABLES)
@Description("Removes a number from a simple variable")
public class StatRemoveFromSimpleVariable extends SimpleVariableBlock {

    public StatRemoveFromSimpleVariable() {
        init(new ChoiceParameter("local", "global", "persistent"), " ", new StringLiteralParameter(), " -= ", double.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return "VariableManager.removeFromVariable(VariableType." + arg(0).toUpperCase() + "," + arg(2) + "," + arg(1) + ");";
    }
}
