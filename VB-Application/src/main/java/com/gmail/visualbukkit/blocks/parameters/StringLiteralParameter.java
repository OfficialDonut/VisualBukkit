package com.gmail.visualbukkit.blocks.parameters;

import org.apache.commons.text.StringEscapeUtils;

public class StringLiteralParameter extends InputParameter {

    public StringLiteralParameter(String label) {
        super(label);
    }

    @Override
    public String toJava() {
        return '"' + StringEscapeUtils.escapeJava(control.getText()) + '"';
    }
}
