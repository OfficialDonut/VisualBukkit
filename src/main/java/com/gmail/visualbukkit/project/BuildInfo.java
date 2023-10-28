package com.gmail.visualbukkit.project;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.util.HashSet;
import java.util.Set;

public class BuildInfo {

    private final Set<String> mavenRepositories = new HashSet<>();
    private final Set<String> mavenDependencies = new HashSet<>();
    private final JavaClassSource mainClass;

    public BuildInfo(JavaClassSource mainClass) {
        this.mainClass = mainClass;
    }

    public Set<String> getMavenRepositories() {
        return mavenRepositories;
    }

    public Set<String> getMavenDependencies() {
        return mavenDependencies;
    }

    public JavaClassSource getMainClass() {
        return mainClass;
    }
}
