package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructCommand;
import com.gmail.visualbukkit.plugin.BuildContext;

@Description("An argument of a command (argument numbers start at 0)")
public class ExprCommandArgument extends ExpressionBlock<String> {

    public ExprCommandArgument() {
        init("argument ", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Command argument must be used in a command", StructCommand.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(ARG_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.getCommandArg(commandArgs," + arg(0) + ")";
    }

    private static final String ARG_METHOD =
            "public static String getCommandArg(String[] args, int i) {\n" +
            "    return args.length > i ? args[i] : null;\n" +
            "}";
}