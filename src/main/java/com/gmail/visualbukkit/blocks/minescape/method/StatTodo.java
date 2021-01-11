package com.gmail.visualbukkit.blocks.minescape.method;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;

@Category(Category.MINESCAPE)
@Description("Something missing")
@BlockColor("#FF33F9ff")
public class StatTodo extends StatementBlock {

    public StatTodo() {
        init("// TODO ", new InputParameter());
    }

    @Override
    public void update() {
        super.update();
        syntaxBox.setOpacity(0.5);
    }

    @Override
    public String toJava() {
        return "//TODO " + arg(0).toString();
    }
}
