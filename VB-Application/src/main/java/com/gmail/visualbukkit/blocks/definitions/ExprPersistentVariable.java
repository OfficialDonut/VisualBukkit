package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class ExprPersistentVariable extends Expression {

    public ExprPersistentVariable() {
        super("expr-persistent-variable", "Persistent Variable", "VB", "The value of a persistent variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter("Var")) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(PluginModule.PERSISTENT_VARIABLES);
            }

            @Override
            public String toJava() {
                return "PluginMain.PERSISTENT_VARIABLES.get(" + arg(0) + ")";
            }
        };
    }
}
