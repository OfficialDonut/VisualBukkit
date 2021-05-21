package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class ExprSimplePersistentVariable extends SimpleExpression {

    public ExprSimplePersistentVariable() {
        super("expr-simple-persistent-variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        StringLiteralParameter input = new StringLiteralParameter();
        input.getStyleClass().add("simple-persistent-variable-field");

        return new Block(this, input) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.VARIABLES);
            }

            @Override
            public String toJava() {
                return "VariableManager.getSimpleVariable(" + arg(0) + ")";
            }
        };
    }
}
