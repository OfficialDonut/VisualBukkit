package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.blocks.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CopyPasteManager {

    private static List<JSONObject> copied;
    private static boolean isStatement;

    public static void copyExpression(Expression.Block block) {
        copied = new ArrayList<>(1);
        copied.add(block.serialize());
        isStatement = false;
    }

    public static void copyStatement(Statement.Block block, boolean copyStack) {
        copied = new ArrayList<>();
        copied.add(block.serialize());
        if (copyStack) {
            List<Statement.Block> blocks = block.getStatementHolder().getBlocks();
            for (int i = blocks.indexOf(block) + 1; i < blocks.size(); i++) {
                copied.add(blocks.get(i).serialize());
            }
        }
        isStatement = true;
    }

    public static Expression.Block pasteExpression() {
        JSONObject json = copied.get(0);
        return BlockRegistry.getExpression(json.getString("=")).createBlock(json);
    }

    public static Statement.Block[] pasteStatement() {
        return copied.stream()
                .map(json -> BlockRegistry.getStatement(json.getString("=")).createBlock(json))
                .toArray(Statement.Block[]::new);
    }

    public static boolean isStatementCopied() {
        return copied != null && isStatement;
    }

    public static boolean isExpressionCopied() {
        return copied != null && !isStatement;
    }
}
