package com.gmail.visualbukkit.blocks;

import org.json.JSONObject;

public sealed abstract class BlockDefinition implements Comparable<BlockDefinition> permits PluginComponent, Statement, Expression {

    private final String id;
    private final String title;
    private final String tag;
    private final String description;

    public BlockDefinition(String id, String title, String tag, String description) {
        this.id = id;
        this.title = title;
        this.tag = "[" + tag + "]";
        this.description = description;
    }

    public abstract BlockSource<?> createSource();

    public abstract BlockNode createBlock();

    public BlockNode createBlock(JSONObject json) {
        BlockNode block = createBlock();
        block.deserialize(json);
        return block;
    }

    @Override
    public int compareTo(BlockDefinition obj) {
        if (equals(obj)) {
            return 0;
        }
        int i = tag.compareTo(obj.tag);
        if (i != 0) {
            return i;
        }
        i = title.compareTo(obj.title);
        return i != 0 ? i : id.compareTo(obj.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            BlockDefinition block = (BlockDefinition) obj;
            return title.equals(block.title) && tag.equals(block.tag);
        }
        return false;
    }

    @Override
    public String toString() {
        return tag + " " + title;
    }

    public final String getID() {
        return id;
    }

    public final String getTitle() {
        return title;
    }

    public final String getTag() {
        return tag;
    }

    public final String getDescription() {
        return description;
    }
}
