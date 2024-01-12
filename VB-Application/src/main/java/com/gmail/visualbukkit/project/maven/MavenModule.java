package com.gmail.visualbukkit.project.maven;

import com.gmail.visualbukkit.project.PluginModule;
import org.json.JSONObject;

public abstract class MavenModule extends PluginModule {

    public MavenModule(String id, String name) {
        super(id, name);
    }

    public abstract JSONObject serialize();
}
