package com.gmail.visualbukkit.blocks.parameters;

import org.apache.commons.text.StringEscapeUtils;

public class StringLiteralParameter extends InputParameter implements BlockParameter {

    @Override
    public String toJava() {
        return '"' + StringEscapeUtils.escapeJava(getText()) + '"';
    }
}
