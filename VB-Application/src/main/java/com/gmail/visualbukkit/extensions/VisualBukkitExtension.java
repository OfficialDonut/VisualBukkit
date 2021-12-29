package com.gmail.visualbukkit.extensions;

import com.gmail.visualbukkit.project.Project;
import org.json.JSONObject;

public abstract class VisualBukkitExtension implements Comparable<VisualBukkitExtension> {

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getAuthor();

    public abstract String getDescription();

    public void activate(Project project) {}

    public void save(Project project, JSONObject data) {}

    public void deactivate(Project project) {}

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
        return getName();
    }
}
