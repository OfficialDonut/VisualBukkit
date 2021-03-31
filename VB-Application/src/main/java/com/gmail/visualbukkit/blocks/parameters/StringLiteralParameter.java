package com.gmail.visualbukkit.blocks.parameters;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringLiteralParameter extends InputParameter {

    @Override
    public String toJava() {
        return '"' + StringEscapeUtils.escapeJava(getText()) + '"';
    }
}
