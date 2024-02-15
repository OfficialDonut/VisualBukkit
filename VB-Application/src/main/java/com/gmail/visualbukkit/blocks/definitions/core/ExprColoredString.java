package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-colored-string", name = "Colored String", description = "A string with '&' color codes")
public class ExprColoredString extends ExpressionBlock {

    public ExprColoredString() {
        addParameter("String", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(String.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "ChatColor.translateAlternateColorCodes('&'," + arg(0, buildInfo) + ")";
    }
}
