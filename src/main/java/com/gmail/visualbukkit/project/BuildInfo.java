package com.gmail.visualbukkit.project;

import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.util.HashSet;
import java.util.Set;

public class BuildInfo {

    private final Set<RemoteRepository> mavenRepositories = new HashSet<>();
    private final Set<DefaultArtifact> mavenDependencies = new HashSet<>();
    private final JavaClassSource mainClass;

    public BuildInfo(JavaClassSource mainClass) {
        this.mainClass = mainClass;
    }

    public Set<RemoteRepository> getMavenRepositories() {
        return mavenRepositories;
    }

    public Set<DefaultArtifact> getMavenDependencies() {
        return mavenDependencies;
    }

    public JavaClassSource getMainClass() {
        return mainClass;
    }
}
