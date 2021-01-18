package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("The characters of a string")
@SuppressWarnings("rawtypes")
public class ExprStringCharacters extends ExpressionBlock<List> {

    public ExprStringCharacters() {
        init("characters of ", String.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".split(\"(?!^)\"))";
    }
}