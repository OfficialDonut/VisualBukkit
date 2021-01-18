package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

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