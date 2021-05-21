package com.gmail.visualbukkit.generator.options;

import jdk.javadoc.doclet.Doclet;

import java.util.Collections;
import java.util.List;

public abstract class BlacklistOption implements Doclet.Option {

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "file containing the canonical names of blacklisted classes";
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return Collections.singletonList("-blacklist");
    }

    @Override
    public String getParameters() {
        return "<file>";
    }
}
