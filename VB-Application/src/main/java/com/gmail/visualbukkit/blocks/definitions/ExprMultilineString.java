package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.MultilineStringParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.Collections;

@BlockDefinition(uid = "expr-multiline-string", name = "Multiline String")
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
    public String generateJava() {
        return arg(0);
    }

    @Override
    public void requestFocus() {
        parameter.requestFocus();
    }
}
