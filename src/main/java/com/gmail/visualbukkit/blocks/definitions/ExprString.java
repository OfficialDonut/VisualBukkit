package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;

import java.util.Collections;

@BlockDefinition(uid = "expr-string", name = "String")
public class ExprString extends ExpressionBlock {

    private final StringParameter parameter = new StringParameter();

    public ExprString() {
        getStyleClass().clear();
        getChildren().setAll(parameter);
        parameters = Collections.singletonList(parameter);
    }

    @Override
    public String generateJava() {
        return arg(0);
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }

    @Override
    public void requestFocus() {
        parameter.requestFocus();
    }
}
