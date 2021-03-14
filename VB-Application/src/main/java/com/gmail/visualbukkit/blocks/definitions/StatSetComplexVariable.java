package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.BinaryChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;

import java.util.List;

public class StatSetComplexVariable extends Statement {

    public StatSetComplexVariable() {
        super("stat-set-complex-variable");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new BinaryChoiceParameter("global", "persistent"), new StringLiteralParameter(), new ExpressionParameter(List.class), new ExpressionParameter(Object.class)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.VARIABLES);
            }

            @Override
            public String toJava() {
                return "VariableManager.setVariable(" + arg(0).equals("persistent") + "," + arg(3) + "," + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
