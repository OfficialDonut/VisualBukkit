package com.gmail.visualbukkit.blocks.parameters;

import org.apache.commons.text.StringEscapeUtils;

public class StringParameter extends InputParameter {

    @Override
    public String generateJava() {
        return '"' + StringEscapeUtils.escapeJava(getText()) + '"';
    }
}
