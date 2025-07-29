package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.HashMap;
import java.util.StringJoiner;

@BlockDefinition(id = "expr-hashmap", name = "HashMap", description = "A new HashMap")
public class ExprHashMap extends SizedExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(HashMap.class);
    }

    @Override
    protected void incrementSize() {
        addParameter("Key", new ExpressionParameter(ClassInfo.of(Object.class)));
        addParameter("Value", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    protected void decrementSize() {
        removeParameters(parameters.size() - 2);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (parameters == null) {
            return "new HashMap()";
        }
        if (buildInfo.getMetadata().putIfAbsent("newHashMap()", true) == null) {
            buildInfo.getMainClass().addMethod(
                    """
                    public static HashMap newHashMap(Object... objects) {
                            HashMap map = new HashMap();
                            for (int i = 0; i < objects.length - 1; i += 2) {
                                map.put(objects[i], objects[i + 1]);
                            }
                            return map;
                    }
                    """);
        }
        StringJoiner joiner = new StringJoiner(",");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.generateJava(buildInfo));
        }
        return "PluginMain.newHashMap(" + joiner + ")";
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html"));
    }
}
