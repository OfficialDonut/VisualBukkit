package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.gui.StyleableHBox;
import javafx.scene.control.Label;

public class StatComment extends Statement {

    public StatComment() {
        super("stat-comment");
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                return "";
            }
        };

        InputParameter inputParameter = new InputParameter();
        block.getParameters().add(inputParameter);
        block.getSyntaxBox().getChildren().set(0, new StyleableHBox(new Label("//"), inputParameter));
        block.getSyntaxBox().setOpacity(0.75);

        return block;
    }
}
