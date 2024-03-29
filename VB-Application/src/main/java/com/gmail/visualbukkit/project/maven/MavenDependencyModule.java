package com.gmail.visualbukkit.project.maven;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;

public class MavenDependencyModule extends MavenModule {

    private final Dependency dependency;

    public MavenDependencyModule(Dependency dependency, boolean userDefined) {
        super(dependency.toString(), String.format("Depend: %s:%s:%s [%s]", dependency.getArtifact().getGroupId(),dependency.getArtifact().getArtifactId(), dependency.getArtifact().getVersion(), dependency.getScope()), userDefined);
        this.dependency = dependency;
    }

    @Override
    public void enable() {
        if (isUserDefined()) {
            try {
                ClassRegistry.register(dependency);
            } catch (IOException | MavenInvocationException | DependencyResolutionException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to register dependency", e);
            }
        }
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        buildInfo.addMavenDependency(dependency);
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("coords", dependency.getArtifact().toString());
        json.put("scope", dependency.getScope());
        return json;
    }

    public static MavenDependencyModule deserialize(JSONObject json) {
        return new MavenDependencyModule(new Dependency(new DefaultArtifact(json.getString("coords")), json.getString("scope")), true);
    }

    public Dependency getDependency() {
        return dependency;
    }
}
