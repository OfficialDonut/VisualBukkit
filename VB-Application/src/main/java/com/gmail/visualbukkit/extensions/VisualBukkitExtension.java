package com.gmail.visualbukkit.extensions;

public abstract class VisualBukkitExtension implements Comparable<VisualBukkitExtension> {

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getAuthor();

    public abstract String getDescription();

    public void activate() {}

    public void deactivate() {}

    @Override
    public final int compareTo(VisualBukkitExtension obj) {
        return getName().compareTo(obj.getName());
    }

    @Override
    public final boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && getName().equals(((VisualBukkitExtension) obj).getName());
    }

    @Override
    public final String toString() {
        return getName() + " v" + getVersion();
    }
}
