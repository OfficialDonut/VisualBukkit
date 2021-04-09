package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

import java.util.regex.Pattern;

public class ExprNumber extends Expression {

    private static final Pattern NUM_PATTERN = Pattern.compile("-?\\d*\\.?\\d*");

    public ExprNumber() {
        super("expr-number", ClassInfo.DOUBLE);
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter();
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!NUM_PATTERN.matcher(input.getText()).matches()) {
                input.setText(oldValue);
            }
        });

        Block block = new Block(this) {
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

        block.getParameters().add(input);
        block.getSyntaxBox().getChildren().set(0, input);
        block.getSyntaxBox().getStyleClass().setAll("expr-number");

        return block;
    }
}
