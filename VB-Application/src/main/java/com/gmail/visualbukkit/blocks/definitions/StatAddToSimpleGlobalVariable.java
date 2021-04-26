package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class StatAddToSimpleGlobalVariable extends Statement {

    public StatAddToSimpleGlobalVariable() {
        super("stat-add-to-simple-global-variable");
    }

    @Override
    public Block createBlock() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("simple-global-variable");

        return new Block(this, inputParameter, new ExpressionParameter(ClassInfo.DOUBLE)) {
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
                String varName = "PluginMain." + ExprSimpleGlobalVariable.getVariable(arg(0));
                return varName + " = (" + varName + " instanceof Number ? ((Number)" + varName + ").doubleValue() : 0) + " + arg(1) + ";";
            }
        };
    }
}
