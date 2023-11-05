package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import org.json.JSONObject;

import java.util.logging.Level;

public abstract class BlockFactory<T extends Block> implements Comparable<BlockFactory<T>> {

    private final Class<?> blockClass;

    public BlockFactory(Class<?> blockClass) {
        this.blockClass = blockClass;
    }

    protected abstract T createUnknown();

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
}
