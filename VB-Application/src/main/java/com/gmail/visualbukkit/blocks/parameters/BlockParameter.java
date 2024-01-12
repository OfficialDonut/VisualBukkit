package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildInfo;

public interface BlockParameter {

    default void updateState() {}

    String generateJava(BuildInfo buildInfo);

    Object serialize();

    void deserialize(Object obj);
}
