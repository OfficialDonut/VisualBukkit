package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Sign;

@Category(Category.WORLD)
@Description("Sets a line of a sign")
public class StatSetSignLine extends StatementBlock {

    public StatSetSignLine() {
        init("set sign line");
        initLine("sign:   ", Sign.class);
        initLine("line:   ", int.class);
        initLine("string: ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setLine(" + arg(1) + "-1," + arg(2) + ");";
    }
}
