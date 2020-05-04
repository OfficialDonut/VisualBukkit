package us.donut.visualbukkit.blocks;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.plugin.PluginBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentBlock extends StatementBlock implements BlockContainer {

    private static Color[] colors = {Color.LIGHTBLUE, Color.CORNFLOWERBLUE, Color.STEELBLUE};
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
    public void onDragDrop() {
        Parent parent = getParent();
        if (parent != null) {
            int level = 0;
            while (!(parent instanceof BlockPane.BlockArea)) {
                if (parent instanceof ParentBlock) {
                    level++;
                }
                parent = parent.getParent();
            }
            Color color = colors[level % colors.length];
            setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        List<StatementBlock> children = getBlocks(false);
        ConfigurationSection childrenSection = section.createSection("children");
        for (int i = 0; i < children.size(); i++) {
            CodeBlock child = children.get(i);
            ConfigurationSection childSection = childrenSection.createSection(String.valueOf(i));
            childSection.set("block-type", child.getClass().getCanonicalName());
            child.unload(childSection);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(ConfigurationSection section) throws Exception {
        super.load(section);
        ConfigurationSection childrenSection = section.getConfigurationSection("children");
        if (childrenSection != null) {
            for (String key : childrenSection.getKeys(false)) {
                ConfigurationSection childSection = childrenSection.getConfigurationSection(key);
                if (childSection != null) {
                    String className = childSection.getString("block-type");
                    if (className != null) {
                        Class<? extends CodeBlock> blockType = (Class<? extends CodeBlock>) Class.forName(className);
                        CodeBlock child = BlockRegistry.getInfo(blockType).createBlock();
                        child.load(childSection);
                        getChildren().add(child);
                    }
                }
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
