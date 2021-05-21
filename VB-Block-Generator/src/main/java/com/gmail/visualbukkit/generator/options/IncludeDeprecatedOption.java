package com.gmail.visualbukkit.generator.options;

import jdk.javadoc.doclet.Doclet;

import java.util.Collections;
import java.util.List;

public abstract class IncludeDeprecatedOption implements Doclet.Option {

    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "generate blocks for deprecated elements";
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return Collections.singletonList("-deprecated");
    }

    @Override
    public String getParameters() {
        return "";
    }
}
