package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;

@BlockDefinition(id = "expr-global-variable", name = "Global Variable", description = "The value of a global variable")
public class ExprGlobalVariable extends ExpressionBlock {

    public ExprGlobalVariable() {
        addParameter("Variable", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        prepareClass(buildInfo.getMainClass());
        return "PluginMain.globalVariables.get(" + arg(0, buildInfo) + ")";
    }

    protected static void prepareClass(JavaClassSource clazz) {
        if (!clazz.hasField("globalVariables")) {
            clazz.addField("public static Map<String, Object> globalVariables = new HashMap<>();");
        }
    }
}
