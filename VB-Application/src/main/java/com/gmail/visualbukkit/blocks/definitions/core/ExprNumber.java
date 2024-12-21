package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.Collections;
import java.util.regex.Pattern;

@BlockDefinition(id = "expr-number", name = "Number", description = "A number")
public class ExprNumber extends ExpressionBlock {

    private static final Pattern NUM_PATTERN = Pattern.compile("-?\\d*\\.?\\d*");
    private final InputParameter parameter = new InputParameter();

    public ExprNumber() {
        getStyleClass().clear();
        getChildren().setAll(parameter);
        parameters = Collections.singletonList(parameter);
        parameter.getStyleClass().add("number-field");
        parameter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!NUM_PATTERN.matcher(newValue).matches()) {
                parameter.setText(oldValue);
            }
        });
    }

    public ExprNumber(Number num) {
        this();
        parameter.setText(num instanceof Double || num instanceof Float ? String.format("%.3f", num.doubleValue()) : String.format("%d", num.longValue()));
    }

    @Override
    public ClassInfo getReturnType() {
        try {
            String number = parameter.getText();
            if (number.contains(".")) {
                Double.parseDouble(number);
                return ClassInfo.of(double.class);
            }
            try {
                Integer.parseInt(number);
                return ClassInfo.of(int.class);
            } catch (NumberFormatException e) {
                Long.parseLong(number);
                return ClassInfo.of(long.class);
            }
        } catch (NumberFormatException e) {
            return ClassInfo.of(Object.class);
        }
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        ClassInfo returnType = getReturnType();
        if (returnType.equals(ClassInfo.of(double.class))) {
            return "(" + parameter.getText() + "D)";
        } else if (returnType.equals(ClassInfo.of(long.class))) {
            return "(" + parameter.getText() + "L)";
        } else if (returnType.equals(ClassInfo.of(int.class))) {
            return parameter.getText();
        } else {
            return "((Object) null)";
        }
    }

    @Override
    public void requestFocus() {
        parameter.requestFocus();
    }
}
