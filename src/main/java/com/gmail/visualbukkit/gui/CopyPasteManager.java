package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.CodeBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CopyPasteManager {

    private static List<BlockDefinition<?>> copied;
    private static List<JSONObject> json;

    public static void copy(CodeBlock block) {
        copied = Collections.singletonList(block.getDefinition());
        json = Collections.singletonList(block.serialize());
    }

    public static void copyStack(StatementBlock block) {
        copied = new ArrayList<>();
        json = new ArrayList<>();
        while (block != null) {
            copied.add(block.getDefinition());
            json.add(block.serialize());
            block = block.getNext();
        }
    }

    public static CodeBlock paste() {
        return copied != null ? copied.get(0).createBlock(json.get(0)) : null;
    }

    public static StatementBlock pasteStack() {
        if (copied != null) {
            StatementBlock first = null;
            StatementBlock last = null;
            for (int i = 0; i < copied.size(); i++) {
                StatementBlock block = (StatementBlock) copied.get(i).createBlock(json.get(i));
                if (first == null) {
                    first = block;
                } else {
                    last.connectNext(block);
                }
                last = block;
            }
            return first;
        }
        return null;
    }

    public static BlockDefinition<?> peek() {
        return copied != null ? copied.get(0) : null;
    }
}
