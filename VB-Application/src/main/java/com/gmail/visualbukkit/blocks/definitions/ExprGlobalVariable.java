package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprGlobalVariable extends Expression {

    public ExprGlobalVariable() {
        super("expr-global-variable", "Global Variable", "VB", "The value of a global variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter("Var")) {
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
        return ExprLocalVariable.getVariable(string).replace("$", "GLOBAL_");
    }
}
