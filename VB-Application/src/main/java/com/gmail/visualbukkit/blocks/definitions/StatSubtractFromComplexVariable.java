package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

public class StatSubtractFromComplexVariable extends Statement {

    public StatSubtractFromComplexVariable() {
        super("stat-subtract-from-complex-variable");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("global", "persistent"), new StringLiteralParameter(), new ExpressionParameter(ClassInfo.LIST), new ExpressionParameter(ClassInfo.DOUBLE)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.VARIABLES);
            }

            @Override
            public String toJava() {
                return "VariableManager.removeFromVariable(" + arg(0).equals("persistent") + "," + arg(3) + "," + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
