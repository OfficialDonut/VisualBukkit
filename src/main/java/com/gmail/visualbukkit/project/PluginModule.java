package com.gmail.visualbukkit.project;

public abstract class PluginModule implements Comparable<PluginModule> {

    private final String uid;
    private final String name;

    public PluginModule(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public void enable() {}

    public void prepareBuild(BuildInfo buildInfo) {}

    @Override
    public int compareTo(PluginModule other) {
        return equals(other) ? 0 : name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PluginModule other && uid.equals(other.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getUID() {
        return uid;
    }
}
