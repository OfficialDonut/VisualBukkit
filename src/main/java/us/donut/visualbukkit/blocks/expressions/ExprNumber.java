package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.regex.Pattern;

@Description({"A number", "Returns: number"})
public class ExprNumber extends ExpressionBlock<Double> {

    private static Pattern numPattern = Pattern.compile("-?\\d*\\.?\\d*");

    @Override
    protected Syntax init() {
        getStyleClass().clear();
        InputParameter inputParameter = new InputParameter();
        inputParameter.setStyle("-fx-background-color: beige;");
        inputParameter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!numPattern.matcher(inputParameter.getText()).matches()) {
                inputParameter.setText(oldValue);
            }
        });
        return new Syntax(inputParameter);
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
