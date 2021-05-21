package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprSimpleGlobalVariable extends SimpleExpression {

    public ExprSimpleGlobalVariable() {
        super("expr-simple-global-variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter();
        input.getStyleClass().add("simple-global-variable-field");

        return new Block(this, input) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                String varName = getVariable(arg(0));
                if (!buildContext.getMainClass().hasField(varName)) {
                    buildContext.getMainClass().addField("public static Object " + varName + ";");
                }
            }

            @Override
            public String toJava() {
                return getVariable(arg(0));
            }
        };
    }

    protected static String getVariable(String string) {
        return ExprSimpleLocalVariable.getVariable(string).replace("$", "GLOBAL_");
    }
}
