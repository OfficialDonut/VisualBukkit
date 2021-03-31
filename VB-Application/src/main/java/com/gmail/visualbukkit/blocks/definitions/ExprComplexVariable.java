package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

public class ExprComplexVariable extends Expression {

    public ExprComplexVariable() {
        super("expr-complex-variable", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("global", "persistent"), new StringLiteralParameter(), new ExpressionParameter(ClassInfo.LIST)) {
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
    }
}
