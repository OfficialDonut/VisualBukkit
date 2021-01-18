package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BlockParameter;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;
import com.gmail.visualbukkit.util.CenteredHBox;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.util.List;
import java.util.StringJoiner;

@Description("A list of objects")
@SuppressWarnings("rawtypes")
public class ExprNewList extends ExpressionBlock<List> {

    private int size = -1;

    public ExprNewList() {
        init("empty list");
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
        return "new ArrayList(Arrays.asList(" + joiner + "))";
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = super.serialize();
        obj.put("size", size);
        return obj;
    }

    @Override
    public void deserialize(JSONObject obj) {
        setSize(obj.optInt("size", -1));
        super.deserialize(obj);
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
                    getChildren().add(new Text(", "));
                }
            }
            getChildren().add(new Text("]"));
        }
    }
}
