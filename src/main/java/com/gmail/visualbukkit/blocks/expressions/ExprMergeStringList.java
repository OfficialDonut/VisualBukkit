package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Merges a list of strings into one string")
public class ExprMergeStringList extends ExpressionBlock<String> {

    public ExprMergeStringList() {
        init(List.class, " merged with ", String.class);
    }

    @Override
    public String toJava() {
        return "String.join(" + arg(1) + ",(List<String>) (Object)" + arg(0) + ")";
    }
}