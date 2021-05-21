package com.gmail.visualbukkit.project;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class BuildContext {

    private JSONObject metadata = new JSONObject();
    private Set<PluginModule> pluginModules = new HashSet<>();
    private Set<String> mavenRepositories = new HashSet<>();
    private Set<String> mavenDependencies = new HashSet<>();
    private Set<String> utilMethods = new HashSet<>();
    private Set<JavaClassSource> utilClasses = new HashSet<>();
    private Set<InputStream> jarDependencies = new HashSet<>();
    private Set<String> localVariables = new HashSet<>();
    private JavaClassSource mainClass;

    public BuildContext(JavaClassSource mainClass) {
        this.mainClass = mainClass;
    }

    public void addPluginModule(PluginModule module) {
        if (pluginModules.add(module)) {
            module.prepareBuild(this);
        }
    }

    public void addMavenRepository(String repository) {
        mavenRepositories.add(repository);
    }

    public void addMavenDependency(String dependency) {
        mavenDependencies.add(dependency);
    }

    public void addUtilMethod(String method) {
        utilMethods.add(method);
    }

    public void addUtilClass(JavaClassSource clazz) {
        utilClasses.add(clazz);
    }

    public void addJarDependency(InputStream inputStream) {
        jarDependencies.add(inputStream);
    }

    public void declareLocalVariable(String varName) {
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

    public JSONObject getMetadata() {
        return metadata;
    }

    public Set<String> getMavenRepositories() {
        return mavenRepositories;
    }

    public Set<String> getMavenDependencies() {
        return mavenDependencies;
    }

    public Set<String> getUtilMethods() {
        return utilMethods;
    }

    public Set<JavaClassSource> getUtilClasses() {
        return utilClasses;
    }

    public Set<InputStream> getJarDependencies() {
        return jarDependencies;
    }

    public JavaClassSource getMainClass() {
        return mainClass;
    }
}
