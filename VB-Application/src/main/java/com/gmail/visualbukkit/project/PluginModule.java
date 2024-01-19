package com.gmail.visualbukkit.project;

public abstract class PluginModule implements Comparable<PluginModule> {

    private final String id;
    private final String name;

    public PluginModule(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void enable();

    public abstract void prepareBuild(BuildInfo buildInfo);

    @Override
    public int compareTo(PluginModule other) {
        return equals(other) ? 0 : name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PluginModule other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }
}
