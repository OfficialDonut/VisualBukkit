package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.BinaryChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

import java.util.List;

public class ExprComplexVariable extends Expression {

    public ExprComplexVariable() {
        super("expr-complex-variable", Object.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new BinaryChoiceParameter("global", "persistent"), new StringLiteralParameter(), new ExpressionParameter(List.class)) {
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
