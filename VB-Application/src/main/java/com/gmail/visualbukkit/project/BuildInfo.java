package com.gmail.visualbukkit.project;

import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.util.HashSet;
import java.util.Set;

public class BuildInfo {

    private final Set<String> localVariables = new HashSet<>();
    private final Set<RemoteRepository> mavenRepositories = new HashSet<>();
    private final Set<Dependency> mavenDependencies = new HashSet<>();
    private final JavaClassSource mainClass;

    public BuildInfo(JavaClassSource mainClass) {
        this.mainClass = mainClass;
    }

    public void addLocalVariable(String varName) {
        localVariables.add(varName);
    }

    public String getLocalVariableDeclarations() {
        StringBuilder builder = new StringBuilder();
        for (String varName : localVariables) {
            builder.append("Object ").append(varName).append(" = null;");
        }
        localVariables.clear();
        return builder.toString();
    }

    public Set<RemoteRepository> getMavenRepositories() {
        return mavenRepositories;
    }

    public Set<Dependency> getMavenDependencies() {
        return mavenDependencies;
    }

    public JavaClassSource getMainClass() {
        return mainClass;
    }
}
