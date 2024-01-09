package com.gmail.visualbukkit;

import com.gmail.visualbukkit.project.Project;

public interface VisualBukkitExtension {

    String getName();

    String getVersion();

    default void open(Project project) {}

    default void save(Project project) {}
}
