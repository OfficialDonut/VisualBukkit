package com.gmail.visualbukkit.blocks;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public abstract class BlockDefinition<T extends CodeBlock<?>> implements Comparable<BlockDefinition<?>> {

    private final String id;
    private final String title;
    private final String[] parameterNames;
    private final String category;

    public BlockDefinition(String id) {
        this.id = id;
        title = BlockRegistry.getString(this, "title", id);
        category = BlockRegistry.getString(this, "category", null);
        String parameterString = BlockRegistry.getString(this, "parameters", null);
        if (parameterString != null) {
            String[] parameters = parameterString.split(",");
            if (parameters.length > 1) {
                int len = parameters[0].length();
                for (int i = 1; i < parameters.length; i++) {
                    if (parameters[i].length() > len) {
                        len = parameters[i].length();
                    }
                }
                len++;
                parameterNames = new String[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    parameterNames[i] = Strings.padEnd(parameters[i] + ":", len, ' ');
                }
            } else {
                parameterNames = new String[]{parameterString + ":"};
            }
        } else {
            parameterNames = null;
        }
    }

    public abstract T createBlock();

    public T createBlock(JSONObject json) {
        T block = createBlock();
        block.deserialize(json);
        return block;
    }

    @Override
    public int compareTo(BlockDefinition<?> definition) {
        if (equals(definition)) {
            return 0;
        }
        int i = StringUtils.compareIgnoreCase(title, definition.title);
        return i == 0 ? id.compareTo(definition.id) : i;
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

    public final String[] getParameterNames() {
        return parameterNames;
    }

    public final String getCategory() {
        return category;
    }
}
