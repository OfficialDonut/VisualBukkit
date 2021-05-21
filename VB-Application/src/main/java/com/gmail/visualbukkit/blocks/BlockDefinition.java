package com.gmail.visualbukkit.blocks;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public abstract class BlockDefinition implements Comparable<BlockDefinition> {

    private final String id;
    private final String title;
    private final String description;
    private final String[] parameterNames;

    public BlockDefinition(String id) {
        this.id = id;
        title = BlockRegistry.getString(this, "title", id);
        description = BlockRegistry.getString(this, "descr", null);
        String parameterString = BlockRegistry.getString(this, "param", null);
        parameterNames = parameterString != null ? parameterString.split(",") : null;
    }

    public abstract CodeBlock createBlock();

    public CodeBlock createBlock(JSONObject json) {
        CodeBlock block = createBlock();
        block.deserialize(json);
        return block;
    }

    @Override
    public int compareTo(BlockDefinition other) {
        if (equals(other)) {
            return 0;
        }
        int i = StringUtils.compareIgnoreCase(title, other.title);
        if (i != 0) {
            return i;
        }
        if (parameterNames == null) {
            return -1;
        }
        if (other.parameterNames == null) {
            return 1;
        }
        i = parameterNames.length - other.parameterNames.length;
        if (i != 0) {
            return i;
        }
        return id.compareTo(other.id);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            return id.equals(((BlockDefinition) obj).id);
        }
        return false;
    }

    @Override
    public String toString() {
        return title;
    }

    public final String getID() {
        return id;
    }

    public final String getTitle() {
        return title;
    }

    public final String getDescription() {
        return description;
    }

    public final String[] getParameterNames() {
        return parameterNames;
    }
}
