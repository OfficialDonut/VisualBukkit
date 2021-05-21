package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildContext;

public interface BlockParameter {

    String toJava();

    Object serialize();

    void deserialize(Object obj);

    default void update() {}

    default void prepareBuild(BuildContext buildContext) {}
}
