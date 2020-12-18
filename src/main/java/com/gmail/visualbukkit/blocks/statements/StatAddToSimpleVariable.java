package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Category(Category.VARIABLES)
@Description("Adds a number to a simple variable")
public class StatAddToSimpleVariable extends StatementBlock {

    public StatAddToSimpleVariable() {
        init(new ChoiceParameter("local", "global", "persistent"), " ", new StringLiteralParameter(), " += ", double.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return "VariableManager.addToVariable(VariableType." + arg(0).toUpperCase() + "," + arg(2) + "," + arg(1) + ");";
    }
}
