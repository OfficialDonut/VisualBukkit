package com.gmail.visualbukkit.blocks.minescape.item;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;

@Category(Category.MINESCAPE)
@Description("Takes item from Player")
public class StatGivePlayerItem extends StatementBlock {

    public StatGivePlayerItem() {
        init("Give ", new StringLiteralParameter(), " x ", new StringLiteralParameter(), " to Player ", boolean.class);
    }

    @Override
    public String toJava() {
        return "";
    }
}
