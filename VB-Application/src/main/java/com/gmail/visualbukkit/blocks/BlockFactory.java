package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.ui.PopOverSelectable;
import org.json.JSONObject;

import java.util.logging.Level;

public class BlockFactory<T extends Block> implements PopOverSelectable, Comparable<BlockFactory<T>> {

    private final Class<?> blockClass;

    protected BlockFactory(Class<?> blockClass) {
        this.blockClass = blockClass;
    }

    @SuppressWarnings("unchecked")
    public T newBlock() {
        try {
            return (T) blockClass.getConstructor().newInstance();
        } catch (Exception e) {
            VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to create block", e);
            return createUnknown();
        }
    }

    public T newBlock(JSONObject json) {
        try {
            T block = newBlock();
            block.deserialize(json);
            return block;
        } catch (Exception e) {
            VisualBukkitApp.getLogger().log(Level.WARNING, "Failed to deserialize block", e);
            T block = createUnknown();
            block.deserialize(json);
            return block;
        }
    }

    @SuppressWarnings("unchecked")
    private T createUnknown() {
        if (PluginComponentBlock.class.isAssignableFrom(blockClass)) {
            return (T) new PluginComponentBlock.Unknown();
        }
        if (StatementBlock.class.isAssignableFrom(blockClass)) {
            return (T) new StatementBlock.Unknown();
        }
        if (ExpressionBlock.class.isAssignableFrom(blockClass)) {
            return (T) new ExpressionBlock.Unknown();
        }
        throw new IllegalStateException();
    }

    @Override
    public int compareTo(BlockFactory o) {
        return getBlockDefinition().name().compareTo(o.getBlockDefinition().name());
    }

    @Override
    public String toString() {
        return getBlockDefinition().name();
    }

    public BlockDefinition getBlockDefinition() {
        return blockClass.getAnnotation(BlockDefinition.class);
    }

    @Override
    public String getPinID() {
        return getBlockDefinition().id();
    }
}
