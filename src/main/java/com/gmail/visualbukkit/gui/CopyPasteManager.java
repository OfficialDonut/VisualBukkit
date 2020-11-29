package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.CodeBlock;
import org.json.JSONObject;

public class CopyPasteManager {

    private static BlockDefinition<?> copied;
    private static JSONObject json;

    public static void copy(CodeBlock block) {
        copied = block.getDefinition();
        json = block.serialize();
    }

    public static CodeBlock paste() {
        return copied != null ? copied.createBlock(json) : null;
    }

    public static BlockDefinition<?> peek() {
        return copied;
    }
}
