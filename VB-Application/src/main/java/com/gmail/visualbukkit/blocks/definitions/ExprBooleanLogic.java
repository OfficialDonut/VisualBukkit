package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.gui.IconButton;
import com.gmail.visualbukkit.gui.StyleableHBox;
import javafx.scene.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

public class ExprBooleanLogic extends Expression {

    private static Map<String, BiFunction<String, String, String>> functions = new TreeMap<>();

    static {
        functions.put("AND", (s1, s2) -> "(" + s1 + "&&" + s2 + ")");
        functions.put("OR", (s1, s2) -> "(" + s1 + "||" + s2 + ")");
        functions.put("XOR", (s1, s2) -> "(" + s1 + "^" + s2 + ")");
    }

    public ExprBooleanLogic() {
        super("expr-boolean-logic", ClassInfo.BOOLEAN);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                BiFunction<String, String, String> function = functions.get(arg(0));
                String java = function.apply(arg(1), arg(2));
                for (int i = 3; i < getParameters().size(); i++) {
                    java = function.apply(java, arg(i));
                }
                return java;
            }
        };

        IconButton increaseSizeButton = new IconButton("plus", null, e -> increaseSize(block));
        IconButton decreaseSizeButton = new IconButton("minus", null, e -> decreaseSize(block));
        Node titleNode = block.getSyntaxBox().getChildren().remove(0);
        block.getSyntaxBox().getChildren().add(new StyleableHBox(titleNode, increaseSizeButton, decreaseSizeButton));
        block.addParameterLines(new ChoiceParameter(functions.keySet()));
        increaseSize(block);
        increaseSize(block);

        return block;
    }

    @Override
    public Block createBlock(JSONObject json) {
        Block block = createBlock();
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            for (int i = 3; i < parameterArray.length(); i++) {
                increaseSize(block);
            }
        }
        block.deserialize(json);
        return block;
    }

    private void increaseSize(Block block) {
        int size = block.getParameters().size() - 1;
        if (size < 10) {
            block.addParameterLine(size + ")", new ExpressionParameter(ClassInfo.BOOLEAN));
        }
    }

    private void decreaseSize(Block block) {
        int size = block.getParameters().size() - 1;
        if (size > 2) {
            block.getParameters().remove(size);
            block.getSyntaxBox().getChildren().remove(size + 1);
        }
    }
}
