package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.Collections;
import java.util.regex.Pattern;

@BlockDefinition(uid = "expr-number", name = "Number")
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

    @Override
    public ClassInfo getReturnType() {
        return parameter.getText().contains(".") ? ClassInfo.of(double.class) : ClassInfo.of(long.class);
    }

    @Override
    public String generateJava() {
        try {
            String number = arg(0);
            if (parameter.getText().contains(".")) {
                Double.parseDouble(number);
                return "(" + number + "D)";
            } else {
                Long.parseLong(number);
                return "(" + number + "L)";
            }
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    @Override
    public void requestFocus() {
        parameter.requestFocus();
    }
}
