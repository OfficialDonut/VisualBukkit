package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class ExprCommandArgument extends Expression {

    public ExprCommandArgument() {
        super("expr-command-argument", String.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(int.class)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-command");
            }

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(ARG_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.getCommandArg(commandArgs," + arg(0) + ")";
            }
        };
    }

    private static final String ARG_METHOD =
            "public static String getCommandArg(String[] args, int i) {\n" +
            "    return args.length > i ? args[i] : null;\n" +
            "}";
}
