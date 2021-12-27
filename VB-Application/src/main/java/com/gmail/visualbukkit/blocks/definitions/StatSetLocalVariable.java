package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class StatSetLocalVariable extends Statement {

    public StatSetLocalVariable() {
        super("stat-set-local-variable", "Set Local Variable", "VB", "Assigns a local variable");
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter("Var");
        input.getControl().getStyleClass().add("local-variable-field");

        return new Block(this, input, new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.declareLocalVariable(ExprLocalVariable.getVariable(arg(0)));
            }

            @Override
            public String toJava() {
                return ExprLocalVariable.getVariable(arg(0)) + " = " + arg(1) + ";";
            }
        };
    }
}
