package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

@Description("A number")
public class ExprNumber extends ExpressionBlock<Double> {

    private static Pattern numPattern = Pattern.compile("-?\\d*\\.?\\d*");
    private InputParameter input = new InputParameter();

    public ExprNumber() {
        init(input);
        input.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!numPattern.matcher(input.getText()).matches()) {
                input.setText(oldValue);
            }
        });
    }

    @Override
    public String toJava() {
        String number = input.toJava();
        try {
            Double.parseDouble(number);
            return number + "d";
        } catch (NumberFormatException e) {
            return "0d";
        }
    }
}
