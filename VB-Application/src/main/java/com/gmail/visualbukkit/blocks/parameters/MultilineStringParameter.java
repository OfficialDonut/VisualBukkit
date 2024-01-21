package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildInfo;
import org.apache.commons.text.StringEscapeUtils;

public class MultilineStringParameter extends MultilineInputParameter {

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return '"' + StringEscapeUtils.escapeJava(getText()) + '"';
    }
}
