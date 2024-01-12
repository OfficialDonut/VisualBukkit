package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.List;

@BlockDefinition(id = "expr-function-value", name = "Function Value")
public class ExprFunctionValue extends ExpressionBlock {

    public ExprFunctionValue() {
        addParameter("Function", new StringParameter());
        addParameter("Arguments", new ExpressionParameter(ClassInfo.of(List.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "PluginMain.function(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ")";
    }
}
