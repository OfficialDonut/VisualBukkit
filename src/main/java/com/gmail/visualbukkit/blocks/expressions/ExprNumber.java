package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;

import java.util.regex.Pattern;

@Description("A number")
public class ExprNumber extends ExpressionBlock<Double> {

    private static Pattern numPattern = Pattern.compile("-?\\d*\\.?\\d*");

    public ExprNumber() {
        InputParameter input = new InputParameter();
        input.setStyle("-fx-control-inner-background: beige;");
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!numPattern.matcher(input.getText()).matches()) {
                input.setText(oldValue);
            }
        });
        init(input);
    }

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
}
