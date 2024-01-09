package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildInfo;

public interface BlockParameter {

    default void updateState() {}

    default void prepareBuild(BuildInfo buildInfo) {}

    String generateJava();

    Object serialize();

    void deserialize(Object obj);
}
