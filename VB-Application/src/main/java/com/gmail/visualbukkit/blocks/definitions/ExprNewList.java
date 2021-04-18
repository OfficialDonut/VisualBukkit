package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.gui.IconButton;
import com.gmail.visualbukkit.gui.StyleableHBox;
import javafx.scene.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.StringJoiner;

public class ExprNewList extends Expression {

    public ExprNewList() {
        super("expr-new-list", ClassInfo.LIST);
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                int size = getParameters().size();
                if (size == 0) {
                    return "new ArrayList()";
                }
                StringJoiner joiner = new StringJoiner(",");
                for (BlockParameter parameter : getParameters()) {
                    joiner.add(parameter.toJava());
                }
                return "new ArrayList(Arrays.asList(" + joiner + "))";
            }
        };

        IconButton increaseSizeButton = new IconButton("plus", null, e -> increaseSize(block));
        IconButton decreaseSizeButton = new IconButton("minus", null, e -> decreaseSize(block));
        Node titleNode = block.getSyntaxBox().getChildren().remove(0);
        block.getSyntaxBox().getChildren().add(new StyleableHBox(titleNode, increaseSizeButton, decreaseSizeButton));

        return block;
    }

    @Override
    public Block createBlock(JSONObject json) {
        Block block = createBlock();
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            for (int i = 0; i < parameterArray.length(); i++) {
                increaseSize(block);
            }
        }
        block.deserialize(json);
        return block;
    }

    private void increaseSize(Block block) {
        int size = block.getParameters().size();
        if (size < 10) {
            block.addParameterLine(size + ")", new ExpressionParameter(ClassInfo.OBJECT));
        }
    }

    private void decreaseSize(Block block) {
        int size = block.getParameters().size();
        if (size > 0) {
            block.getParameters().remove(size - 1);
            block.getSyntaxBox().getChildren().remove(size);
        }
    }
}
