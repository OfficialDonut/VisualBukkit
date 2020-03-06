package us.donut.visualbukkit.blocks;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.plugin.PluginBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentBlock extends StatementBlock implements BlockContainer {

    private double contextMenuYCoord;

    public ParentBlock() {
        getStyleClass().set(0, "parent-block");
        DragManager.enableBlockContainer(this);
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
    public boolean canAccept(CodeBlock block, double yCoord) {
        boolean valid = false;
        if (block instanceof StatementBlock) {
            Pane parent = (Pane) block.getParent();
            int currentIndex = -1;
            if (parent != null) {
                currentIndex = parent.getChildren().indexOf(block);
                parent.getChildren().remove(currentIndex);
            }
            int index = DragManager.getIndexAt(this, yCoord);
            getChildren().add(index, block);
            valid = PluginBuilder.isCodeValid(block.getBlockPane());
            getChildren().remove(index);
            if (parent != null) {
                parent.getChildren().add(currentIndex, block);
            }
        }
        return valid;
    }

    @Override
    public void accept(CodeBlock block, double yCoord) {
        UndoManager.capture();
        Pane parent = (Pane) block.getParent();
        if (parent != null) {
            parent.getChildren().remove(block);
        }
        getChildren().add(DragManager.getIndexAt(this, yCoord), block);
        Platform.runLater(block::onDragDrop);
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        List<StatementBlock> children = getBlocks(false);
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
        getBlocks(true).forEach(block -> childJava.append(block.toJava()));
        return childJava.toString();
    }

    @Override
    public List<StatementBlock> getBlocks(boolean ignoreDisabled) {
        List<StatementBlock> blocks = new ArrayList<>();
        for (Node child : getChildren()) {
            if (child instanceof StatementBlock && (!ignoreDisabled || ((StatementBlock) child).isEnabled())) {
                blocks.add((StatementBlock) child);
            }
        }
        return blocks;
    }
}
