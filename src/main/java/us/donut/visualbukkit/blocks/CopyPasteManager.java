package us.donut.visualbukkit.blocks;

import javafx.scene.layout.Pane;
import org.bukkit.configuration.file.YamlConfiguration;
import us.donut.visualbukkit.VisualBukkit;

public class CopyPasteManager {

    private static BlockInfo<?> copiedBlock;
    private static YamlConfiguration blockData;

    public static void copy(CodeBlock block) {
        copiedBlock = BlockRegistry.getInfo(block.getClass());
        blockData = new YamlConfiguration();
        block.unload(blockData);
    }

    public static void paste(Pane pane, double yCoord) {
        if (copiedBlock != null && blockData != null) {
            CodeBlock block = copiedBlock.createBlock();
            try {
                block.load(blockData);
                if (DragManager.canMove(block, pane, yCoord)) {
                    UndoManager.capture();
                    DragManager.move(block, pane, yCoord);
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
