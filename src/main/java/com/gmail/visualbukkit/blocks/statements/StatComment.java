package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;

@Description("A comment (has no effect)")
public class StatComment extends StatementBlock {

    public StatComment() {
        init("// ", new InputParameter());
    }

    @Override
    public void update() {
        super.update();
        syntaxBox.setOpacity(0.5);
    }

    @Override
    public String toJava() {
        return "";
    }
}
