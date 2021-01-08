package com.gmail.visualbukkit.plugin;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BuildContext {

    private Set<PluginModule> pluginModules = new HashSet<>();
    private Set<String> mavenRepositories = new HashSet<>();
    private Set<String> mavenDependencies = new HashSet<>();
    private Set<InputStream> jarDependencies = new HashSet<>();
    private Set<String> utilMethods = new HashSet<>();
    private Set<Class<?>> utilClasses = new HashSet<>();
    private JavaClassSource mainClass;

    public BuildContext(JavaClassSource mainClass) {
        this.mainClass = mainClass;
    }

    public void addPluginModules(PluginModule... modules) {
        for (PluginModule module : modules) {
            if (pluginModules.add(module)) {
                module.prepareBuild(this);
            }
        }
    }

    public void addMavenRepositories(String... repositories) {
        mavenRepositories.addAll(Arrays.asList(repositories));
    }

    public void addMavenDependencies(String... dependencies) {
        mavenDependencies.addAll(Arrays.asList(dependencies));
    }

    public void addJarDependencies(InputStream... inputStreams) {
        jarDependencies.addAll(Arrays.asList(inputStreams));
    }

    public void addUtilMethods(String... methods) {
        utilMethods.addAll(Arrays.asList(methods));
    }

    public void addUtilClasses(Class<?>... classes) {
        utilClasses.addAll(Arrays.asList(classes));
    }

    public Set<String> getMavenRepositories() {
        return mavenRepositories;
    }

    public Set<String> getMavenDependencies() {
        return mavenDependencies;
    }

    public Set<InputStream> getJarDependencies() {
        return jarDependencies;
    }

    public Set<String> getUtilMethods() {
        return utilMethods;
    }

    public Set<Class<?>> getUtilClasses() {
        return utilClasses;
    }

    public JavaClassSource getMainClass() {
        return mainClass;
    }
}
