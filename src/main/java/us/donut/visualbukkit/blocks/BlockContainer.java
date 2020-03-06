package us.donut.visualbukkit.blocks;

import java.util.List;

public interface BlockContainer {

    boolean canAccept(CodeBlock block, double yCoord);

    void accept(CodeBlock block, double yCoord);

    List<? extends CodeBlock> getBlocks(boolean ignoreDisabled);
}
