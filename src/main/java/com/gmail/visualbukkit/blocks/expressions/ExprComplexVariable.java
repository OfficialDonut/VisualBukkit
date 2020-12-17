package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

import java.util.List;

@Description("The value of a complex variable")
public class ExprComplexVariable extends ExpressionBlock<Object> {

    public ExprComplexVariable() {
        init(new ChoiceParameter("local", "global", "persistent"), " ", List.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return "VariableManager.getVariable(VariableType." + arg(0).toUpperCase() + "," + arg(1) + ".toArray())";
    }
}
