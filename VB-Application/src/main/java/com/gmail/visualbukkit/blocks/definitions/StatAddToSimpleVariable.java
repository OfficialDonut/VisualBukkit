package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class StatAddToSimpleVariable extends Statement {

    public StatAddToSimpleVariable() {
        super("stat-add-to-simple-variable");
    }

    @Override
    public Block createBlock() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("simple-local-variable");

        return new Block(this, inputParameter, new ExpressionParameter(double.class)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.declareLocalVariable(ExprSimpleLocalVariable.getVariable(arg(0)));
            }

            @Override
            public String toJava() {
                String varName = ExprSimpleLocalVariable.getVariable(arg(0));
                return varName + " = (" + varName + " instanceof Number ? ((Number)" + varName + ").doubleValue() : 0) + " + arg(1) + ";";
            }
        };
    }
}
