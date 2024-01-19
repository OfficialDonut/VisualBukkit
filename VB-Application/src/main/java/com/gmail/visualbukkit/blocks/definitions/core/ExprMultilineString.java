package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.MultilineStringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.Collections;

@BlockDefinition(id = "expr-multiline-string", name = "Multiline String", description = "A multiline string literal")
public class ExprMultilineString extends ExpressionBlock {

    private final MultilineStringParameter parameter = new MultilineStringParameter();

    public ExprMultilineString() {
        getStyleClass().clear();
        getChildren().setAll(parameter);
        parameters = Collections.singletonList(parameter);
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
