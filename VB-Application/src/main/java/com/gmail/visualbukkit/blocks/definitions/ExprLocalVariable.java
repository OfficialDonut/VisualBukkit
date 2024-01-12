package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.regex.Pattern;

@BlockDefinition(id = "expr-local-variable", name = "Local Variable")
public class ExprLocalVariable extends ExpressionBlock {

    protected static final Pattern VAR_PATTERN = Pattern.compile("\\$[a-z0-9]{32}");
    private final StringParameter parameter = new StringParameter();

    public ExprLocalVariable() {
        getStyleClass().clear();
        getChildren().setAll(parameter);
        parameters = Collections.singletonList(parameter);
        parameter.getStyleClass().add("local-variable-field");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        buildInfo.addLocalVariable(getVariable(arg(0, buildInfo)));
        return getVariable(arg(0, buildInfo));
    }

    @Override
    public void requestFocus() {
        parameter.requestFocus();
    }

    protected static String getVariable(String string) {
        return "$" + Hashing.murmur3_128().hashString(string, StandardCharsets.UTF_8);
    }
}
