package com.gmail.visualbukkit.project;

import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BuildInfo {

    private final Map<Object, Object> metadata = new HashMap<>();
    private final Set<String> localVariables = new HashSet<>();
    private final Set<RemoteRepository> mavenRepositories = new HashSet<>();
    private final Set<Dependency> mavenDependencies = new HashSet<>();
    private final Set<JavaClassSource> classes = new HashSet<>();
    private final JavaClassSource mainClass;
    private final boolean debugMode;

    public BuildInfo(JavaClassSource mainClass, boolean debugMode) {
        this.mainClass = mainClass;
        this.debugMode = debugMode;
    }

    public void addMavenRepository(RemoteRepository repository) {
        mavenRepositories.add(repository);
    }

    public void addMavenDependency(Dependency dependency) {
        mavenDependencies.add(dependency);
    }

    public void addClass(JavaClassSource clazz) {
        classes.add(clazz);
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

    public Set<JavaClassSource> getClasses() {
        return classes;
    }

    public JavaClassSource getMainClass() {
        return mainClass;
    }

    public Map<Object, Object> getMetadata() {
        return metadata;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
