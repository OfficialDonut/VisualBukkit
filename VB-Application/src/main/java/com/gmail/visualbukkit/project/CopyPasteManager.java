package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CopyPasteManager {

    private static final BooleanProperty statementCopied = new SimpleBooleanProperty(false);
    private static final BooleanProperty expressionCopied = new SimpleBooleanProperty(false);
    private static List<JSONObject> serializedBlocks;

    public static void copyStatement(StatementBlock block) {
        serializedBlocks = Collections.singletonList(block.serialize());
        statementCopied.set(true);
        expressionCopied.set(false);
    }

    public static void copyStatements(List<StatementBlock> blocks) {
        serializedBlocks = blocks.stream().map(Block::serialize).toList();
        statementCopied.set(true);
        expressionCopied.set(false);
    }

    public static void copyExpression(ExpressionBlock block) {
        serializedBlocks = Collections.singletonList(block.serialize());
        expressionCopied.set(true);
        statementCopied.set(false);
    }

    public static ExpressionBlock pasteExpression() {
        ExpressionBlock block = BlockRegistry.newExpression(serializedBlocks.get(0));
        block.setId(UUID.randomUUID().toString());
        return block;
    }

    public static StatementBlock[] pasteStatement() {
        return serializedBlocks.stream()
                .map(BlockRegistry::newStatement)
                .peek(b -> b.setId(UUID.randomUUID().toString()))
                .toArray(StatementBlock[]::new);
    }

    public static ReadOnlyBooleanProperty statementCopiedProperty() {
        return statementCopied;
    }

    public static ReadOnlyBooleanProperty expressionCopiedProperty() {
        return expressionCopied;
    }
}
