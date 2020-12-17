package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

@Description("The value of a simple variable")
public class ExprSimpleVariable extends ExpressionBlock<Object> {

    public ExprSimpleVariable() {
        init(new ChoiceParameter("local", "global", "persistent"), " ", new StringLiteralParameter());
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.VARIABLES);
    }

    @Override
    public String toJava() {
        return "VariableManager.getVariable(VariableType." + arg(0).toUpperCase() + "," + arg(1) + ")";
    }
}
