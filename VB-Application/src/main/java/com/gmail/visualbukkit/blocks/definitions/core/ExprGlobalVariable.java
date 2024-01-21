package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.google.common.hash.Hashing;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.nio.charset.StandardCharsets;

@BlockDefinition(id = "expr-global-variable", name = "Global Variable", description = "The value of a global variable")
public class ExprGlobalVariable extends ExpressionBlock {

    public ExprGlobalVariable() {
        addParameter("Variable", new InputParameter());
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        String variable = getVariable(arg(0, buildInfo));
        declareVariable(buildInfo.getMainClass(), variable);
        return "PluginMain." + variable;
    }

    protected static String getVariable(String string) {
        return "$GLOBAL_" + Hashing.murmur3_128().hashString(string, StandardCharsets.UTF_8);
    }

    protected static void declareVariable(JavaClassSource clazz, String variable) {
        if (!clazz.hasField(variable)) {
            clazz.addField("public static Object " + variable + ";");
        }
    }
}
