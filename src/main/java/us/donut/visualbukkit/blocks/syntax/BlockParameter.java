package us.donut.visualbukkit.blocks.syntax;

import us.donut.visualbukkit.util.DataConfig;

public interface BlockParameter {

    String toJava();

    void saveTo(DataConfig config);

    void loadFrom(DataConfig config);

    default void update() {}
}
