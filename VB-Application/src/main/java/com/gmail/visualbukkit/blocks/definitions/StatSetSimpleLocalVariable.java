package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class StatSetSimpleLocalVariable extends Statement {

    public StatSetSimpleLocalVariable() {
        super("stat-set-simple-local-variable");
    }

    @Override
    public Block createBlock() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("simple-local-variable");

        return new Block(this, inputParameter, new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.declareLocalVariable(ExprSimpleLocalVariable.getVariable(arg(0)));
            }

            @Override
            public String toJava() {
                return ExprSimpleLocalVariable.getVariable(arg(0)) + " = " + arg(1) + ";";
            }
        };
    }
}
