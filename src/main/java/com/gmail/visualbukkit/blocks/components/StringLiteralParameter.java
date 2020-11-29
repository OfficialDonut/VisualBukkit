package com.gmail.visualbukkit.blocks.components;

import org.apache.commons.lang.StringEscapeUtils;

public class StringLiteralParameter extends InputParameter {

    @Override
    public String toJava() {
        return "\"" + StringEscapeUtils.escapeJava(super.toJava()) + "\"";
    }
}
