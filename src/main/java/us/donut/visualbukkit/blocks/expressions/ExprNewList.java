package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.util.CenteredHBox;
import us.donut.visualbukkit.util.DataConfig;

import java.util.List;
import java.util.StringJoiner;

@Description({"A list of objects", "Returns: list"})
public class ExprNewList extends ExpressionBlock<List> {

    private int size = -1;

    @Override
    protected Syntax init() {
        setStyle("-fx-spacing: 1;");
        return new Syntax("empty list");
    }

    @Override
    public void update() {
        super.update();
        if (size < 0) {
            Dialog<Integer> dialog = new Dialog<>();
            Spinner<Integer> spinner = new Spinner<>();
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
            dialog.getDialogPane().setContent(new CenteredHBox(new Label("Size of list: "), spinner));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.setResultConverter(buttonType -> spinner.getValue());
            setSize(dialog.showAndWait().orElse(0));
        }
    }

    @Override
    public String toJava() {
        if (size == 0) {
            return "new ArrayList()";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.toJava());
        }
        return "Arrays.asList(" + joiner + ")";
    }

    @Override
    public void loadFrom(DataConfig config) {
        setSize(config.getConfigList("parameters").size());
        super.loadFrom(config);
    }

    private void setSize(int size) {
        this.size = size;
        if (size > 0) {
            getChildren().clear();
            getChildren().add(new Text("["));
            for (int i = 0; i < size; i++) {
                ExpressionParameter exprParameter = new ExpressionParameter(Object.class);
                parameters.add(exprParameter);
                getChildren().add(exprParameter);
                if (i != size - 1) {
                    getChildren().add(new Text(","));
                }
            }
            getChildren().add(new Text("]"));
        }
    }
}
