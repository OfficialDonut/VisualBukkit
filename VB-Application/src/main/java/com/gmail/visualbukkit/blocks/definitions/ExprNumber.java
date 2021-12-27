package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

import java.util.regex.Pattern;

public class ExprNumber extends SimpleExpression {

    private static final Pattern NUM_PATTERN = Pattern.compile("-?\\d*\\.?\\d*");

    public ExprNumber() {
        super("expr-number", "Number", "Math", "A number");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter("");
        input.getControl().getStyleClass().add("number-field");
        input.getControl().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!NUM_PATTERN.matcher(input.getControl().getText()).matches()) {
                input.getControl().setText(oldValue);
            }
        });

        return new Block(this, input) {
            @Override
            public String toJava() {
                String number = arg(0);
                try {
                    Double.parseDouble(number);
                    return number + "d";
                } catch (NumberFormatException e) {
                    return "0d";
                }
            }
        };
    }
}
