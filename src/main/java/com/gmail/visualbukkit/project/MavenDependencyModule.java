package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class MavenDependencyModule extends MavenModule {

    private final DefaultArtifact artifact;

    public MavenDependencyModule(DefaultArtifact artifact) {
        super(artifact.toString(), String.format("%s:%s:%s %s", artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion(), artifact.getProperties()));
        this.artifact = artifact;
    }

    @Override
    public void enable() {
        try {
            ClassRegistry.register(artifact);
        } catch (IOException | MavenInvocationException | DependencyResolutionException e) {
            VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to register dependency", e);
        }
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("coords", artifact.toString());
        for (Map.Entry<String, String> entry : artifact.getProperties().entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json;
    }

    public static MavenDependencyModule deserialize(JSONObject json) {
        Map<String, String> properties = new HashMap<>();
        Set<String> keys = json.keySet();
        if (keys.size() > 1) {
            for (String key : json.keySet()) {
                if (!key.equals("coords")) {
                    properties.put(key, json.getString(key));
                }
            }
        }
        return new MavenDependencyModule(new DefaultArtifact(json.getString("coords"), properties));
    }

    public DefaultArtifact getArtifact() {
        return artifact;
    }
}
