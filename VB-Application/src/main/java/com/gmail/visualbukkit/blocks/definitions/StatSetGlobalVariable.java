package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class StatSetGlobalVariable extends Statement {

    public StatSetGlobalVariable() {
        super("stat-set-global-variable", "Set Global Variable", "VB", "Assigns a global variable");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter("Var"), new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                String varName = ExprGlobalVariable.getVariable(arg(0));
                if (!buildContext.getMainClass().hasField(varName)) {
                    buildContext.getMainClass().addField("public static Object " + varName + ";");
                }
            }

            @Override
            public String toJava() {
                return "PluginMain." + ExprGlobalVariable.getVariable(arg(0)) + " = " + arg(1) + ";";
            }
        };
    }
}
