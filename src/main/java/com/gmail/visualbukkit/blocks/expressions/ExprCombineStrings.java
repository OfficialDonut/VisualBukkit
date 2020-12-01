package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;

@Description("Combines two strings into one string")
public class ExprCombineStrings extends ExpressionBlock<String> {

    private ExpressionParameter string1 = new ExpressionParameter(String.class);
    private ExpressionParameter string2 = new ExpressionParameter(String.class);

    public ExprCombineStrings() {
        init(string1, " + ", string2);
    }

    @Override
    public String toJava() {
        return "(" + string1.toJava() + "+" + string2.toJava() + ")";
    }

    public ExpressionParameter getString1() {
        return string1;
    }

    public ExpressionParameter getString2() {
        return string2;
    }
}
