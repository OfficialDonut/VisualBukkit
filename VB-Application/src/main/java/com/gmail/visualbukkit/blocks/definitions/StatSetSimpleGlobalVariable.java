package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class StatSetSimpleGlobalVariable extends Statement {

    public StatSetSimpleGlobalVariable() {
        super("stat-set-simple-global-variable");
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter();
        input.getStyleClass().add("simple-global-variable-field");

        return new Block(this, input, new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                String varName = ExprSimpleGlobalVariable.getVariable(arg(0));
                if (!buildContext.getMainClass().hasField(varName)) {
                    buildContext.getMainClass().addField("public static Object " + varName + ";");
                }
            }

            @Override
            public String toJava() {
                return "PluginMain." + ExprSimpleGlobalVariable.getVariable(arg(0)) + " = " + arg(1) + ";";
            }
        };
    }
}
