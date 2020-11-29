package com.gmail.visualbukkit.plugin;

import java.util.function.Consumer;

public class PluginModule {

    private Consumer<BuildContext> buildAction;

    public PluginModule(Consumer<BuildContext> buildAction) {
        this.buildAction = buildAction;
    }

    public void prepareBuild(BuildContext buildContext) {
        buildAction.accept(buildContext);
    }
}
