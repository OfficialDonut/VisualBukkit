package com.gmail.visualbukkit.project;

public abstract class PluginModule implements Comparable<PluginModule> {

    private final String name;
    private final String uid;

    public PluginModule(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public void enable() {}

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
