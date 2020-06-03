package us.donut.visualbukkit.blocks;

import org.bukkit.configuration.file.YamlConfiguration;
import us.donut.visualbukkit.VisualBukkit;

public class CopyPasteManager {

    private static BlockInfo<?> copiedBlock;
    private static YamlConfiguration blockData;

    public static void copy(CodeBlock block) {
        copiedBlock = BlockRegistry.getInfo(block);
        blockData = new YamlConfiguration();
        block.unload(blockData);
    }

    public static void paste(BlockContainer container, double yCoord) {
        if (copiedBlock != null && blockData != null) {
            CodeBlock block = copiedBlock.createBlock();
            try {
                block.load(blockData);
                if (container.canAccept(block, yCoord)) {
                    container.accept(block, yCoord);
                } else {
                    VisualBukkit.displayError("Cannot paste block here");
                }
            } catch (Exception e) {
                VisualBukkit.displayException("Failed to paste block", e);
            }
        } else {
            VisualBukkit.displayError("No block has been copied");
        }
    }
}
