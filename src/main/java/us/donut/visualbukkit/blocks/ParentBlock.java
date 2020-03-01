package us.donut.visualbukkit.blocks;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentBlock extends StatementBlock {

    private double contextMenuYCoord;

    public ParentBlock() {
        getStyleClass().set(0, "parent-block");
        DragManager.enableDragTarget(this);
        setOnContextMenuRequested(e -> {
            if (isEnabled()) {
                getContextMenu().show(this, e.getScreenX(), e.getScreenY());
                contextMenuYCoord = e.getY();
            } else {
                getDisabledContextMenu().show(this, e.getScreenX(), e.getScreenY());
            }
            e.consume();
        });
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> CopyPasteManager.paste(this, contextMenuYCoord));
        getContextMenu().getItems().add(1, pasteItem);
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        List<StatementBlock> children = getBlocks();
        for (int i = 0; i < children.size(); i++) {
            CodeBlock child = children.get(i);
            String className = child.getClass().getCanonicalName().replace('.', '_');
            child.unload(section.createSection("children." + i + className));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(ConfigurationSection section) throws Exception {
        super.load(section);
        ConfigurationSection childrenSection = section.getConfigurationSection("children");
        if (childrenSection != null) {
            for (String key : childrenSection.getKeys(false)) {
                String className = key.substring(1).replace('_', '.');
                Class<? extends CodeBlock> blockClass = (Class<? extends CodeBlock>) Class.forName(className);
                CodeBlock child = BlockRegistry.getInfo(blockClass).createBlock();
                child.load(childrenSection.getConfigurationSection(key));
                getChildren().add(child);
            }
        }
    }

    public String getChildJava() {
        StringBuilder childJava = new StringBuilder();
        getBlocks().forEach(block -> childJava.append(block.toJava()));
        return childJava.toString();
    }

    public List<StatementBlock> getBlocks() {
        List<StatementBlock> blocks = new ArrayList<>();
        for (Node child : getChildren()) {
            if (child instanceof StatementBlock && ((StatementBlock) child).isEnabled()) {
                blocks.add((StatementBlock) child);
            }
        }
        return blocks;
    }
}
