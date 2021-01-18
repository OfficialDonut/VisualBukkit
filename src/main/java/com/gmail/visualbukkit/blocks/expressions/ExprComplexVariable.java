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
        return arg(0).equals("local") ?
                ("VariableManager.getLocalVariable(localVariableScope," + arg(1) + ".toArray())") :
                ("VariableManager.getVariable(" + arg(0).equals("persistent") + "," + arg(1) + ".toArray())");
    }
}
