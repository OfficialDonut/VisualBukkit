package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

import java.util.List;

@Category(Category.VARIABLES)
@Description("Removes a number from a complex variable")
public class StatRemoveFromComplexVariable extends StatementBlock {

    public StatRemoveFromComplexVariable() {
        init(new ChoiceParameter("local", "global", "persistent"), " ", List.class, " -= ", double.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return "VariableManager.removeFromVariable(VariableType." + arg(0).toUpperCase() + "," + arg(2) + "," + arg(1) + ".toArray());";
    }
}
