package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class StatSetSimpleLocalVariable extends Statement {

    public StatSetSimpleLocalVariable() {
        super("stat-set-simple-local-variable");
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter();
        input.getStyleClass().add("simple-local-variable-field");

        return new Block(this, input, new ExpressionParameter(ClassInfo.OBJECT)) {
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
