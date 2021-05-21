package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class StatSetSimplePersistentVariable extends Statement {

    public StatSetSimplePersistentVariable() {
        super("stat-set-simple-persistent-variable");
    }

    @Override
    public Block createBlock() {
        StringLiteralParameter input = new StringLiteralParameter();
        input.getStyleClass().add("simple-persistent-variable-field");

        return new Block(this, input, new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.VARIABLES);
            }

            @Override
            public String toJava() {
                return "VariableManager.setSimpleVariable(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }
}
