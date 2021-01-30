package com.gmail.visualbukkit.blocks.minescape.method;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.minescape.MinescapeType;

@Category(Category.MINESCAPE)
@Description("Runs code if a condition is true")
public class StatIfStatement extends ParentBlock {

    public StatIfStatement() {
        init("if ", MinescapeType.class);
    }

    @Override
    public String toJava() {
        return "";
    }
}
