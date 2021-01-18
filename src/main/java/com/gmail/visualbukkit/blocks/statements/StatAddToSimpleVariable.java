package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.SimpleVariableBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Category(Category.VARIABLES)
@Description("Adds a number to a simple variable")
public class StatAddToSimpleVariable extends SimpleVariableBlock {

    public StatAddToSimpleVariable() {
        init(new ChoiceParameter("local", "global", "persistent"), " ", new StringLiteralParameter(), " += ", double.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return arg(0).equals("local") ?
                ("VariableManager.addToLocalVariable(localVariableScope," + arg(2) + "," + arg(1) + ");") :
                ("VariableManager.addToVariable(" + arg(0).equals("persistent") + "," + arg(2) + "," + arg(1) + ");");
    }
}
