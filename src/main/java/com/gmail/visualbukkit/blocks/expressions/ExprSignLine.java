package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Sign;

@Description("A line of a sign")
public class ExprSignLine extends ExpressionBlock<String> {

    public ExprSignLine() {
        init("line ", int.class, " of ", Sign.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getLine(" + arg(0) + "-1)";
    }
}