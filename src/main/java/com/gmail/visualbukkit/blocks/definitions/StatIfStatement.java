package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;
import javafx.scene.control.CheckBox;
import org.json.JSONObject;

@BlockDefinition(uid = "stat-if-statement", name = "If Statement")
public class StatIfStatement extends ContainerBlock {

    private final CheckBox negatedCheckBox = new CheckBox("Negated");

    public StatIfStatement() {
        addToHeader(negatedCheckBox);
        addParameter("Condition", new ExpressionParameter(ClassInfo.of(boolean.class)));
    }

    @Override
    public String generateJava() {
        return "if (" + (negatedCheckBox.isSelected() ? "!" : "") + arg(0) + ") {" + generateChildrenJava() + "}";
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = super.serialize();
        json.put("negated", negatedCheckBox.isSelected());
        return json;
    }

    @Override
    public void deserialize(JSONObject json) {
        super.deserialize(json);
        negatedCheckBox.setSelected(json.optBoolean("negated"));
    }
}
