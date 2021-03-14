package com.gmail.visualbukkit.blocks;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CopyPasteManager {

    private static List<BlockDefinition<?>> copied;
    private static List<JSONObject> json;

    public static void copyExpression(Expression.Block block) {
        copied = Collections.singletonList(block.getDefinition());
        json = Collections.singletonList(block.serialize());
    }

    public static void copyStatement(Statement.Block block, boolean copyStack) {
        copied = new ArrayList<>();
        json = new ArrayList<>();
        copied.add(block.getDefinition());
        json.add(block.serialize());
        if (copyStack) {
            block = block.getNext().getConnected();
            while (block != null) {
                copied.add(block.getDefinition());
                json.add(block.serialize());
                block = block.getNext().getConnected();
            }
        }
    }

    public static Expression.Block pasteExpression() {
        return (Expression.Block) copied.get(0).createBlock(json.get(0));
    }

    public static Statement.Block pasteStatement() {
        Statement.Block first = null;
        Statement.Block last = null;
        for (int i = 0; i < copied.size(); i++) {
            Statement.Block block = (Statement.Block) copied.get(i).createBlock(json.get(i));
            if (first == null) {
                first = block;
            } else {
                last.getNext().connect(block).run();
            }
            last = block;
        }
        return first;
    }

    public static BlockDefinition<?> peek() {
        return copied != null ? copied.get(0) : null;
    }
}
