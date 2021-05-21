package com.gmail.visualbukkit.generator.options;

import jdk.javadoc.doclet.Doclet;

import java.util.Collections;
import java.util.List;

public abstract class PluginModuleOption implements Doclet.Option {

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "the plugin module required by the generated blocks";
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return Collections.singletonList("-module");
    }

    @Override
    public String getParameters() {
        return "<string>";
    }
}
