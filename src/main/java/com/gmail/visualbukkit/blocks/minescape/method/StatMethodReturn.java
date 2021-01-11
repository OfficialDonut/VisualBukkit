package com.gmail.visualbukkit.blocks.minescape.method;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructFunction;

@Category(Category.MINESCAPE)
@Description("Returns")
public class StatMethodReturn extends StatementBlock {

    public StatMethodReturn() {
        init("return");
    }

    @Override
    public String toJava() {
        return "";
    }
}
