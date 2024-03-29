package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.Collections;

@BlockDefinition(id = "expr-string", name = "String", description = "A string literal")
public class ExprString extends ExpressionBlock {

    private final StringParameter parameter = new StringParameter();

    public ExprString() {
        getStyleClass().clear();
        getChildren().setAll(parameter);
        parameters = Collections.singletonList(parameter);
    }

    public ExprString(String string) {
        this();
        parameter.setText(string);
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(String.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return arg(0, buildInfo);
    }

    @Override
    public void requestFocus() {
        parameter.requestFocus();
    }
}
