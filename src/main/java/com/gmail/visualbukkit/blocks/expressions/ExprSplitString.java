package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Splits a string at every occurrence of another string")
@SuppressWarnings("rawtypes")
public class ExprSplitString extends ExpressionBlock<List> {

    public ExprSplitString() {
        init(String.class, " split at ", String.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".split(" + arg(1) + "))";
    }
}