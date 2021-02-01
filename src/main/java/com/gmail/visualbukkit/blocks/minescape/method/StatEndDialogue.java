package com.gmail.visualbukkit.blocks.minescape.method;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.MINESCAPE)
@Description("Ends Dialogue")
public class StatEndDialogue extends StatementBlock {
    public StatEndDialogue() {
        init("END");
    }

    @Override
    public String toJava() {
        return null;
    }
}
