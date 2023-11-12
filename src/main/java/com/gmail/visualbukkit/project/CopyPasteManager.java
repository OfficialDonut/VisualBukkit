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

public class CopyPasteManager {

    private static final BooleanProperty statementCopied = new SimpleBooleanProperty(false);
    private static final BooleanProperty expressionCopied = new SimpleBooleanProperty(false);
    private static List<JSONObject> serializedBlocks;

    public static void copyStatement(StatementBlock block, boolean copyStack) {
        if (copyStack) {
            serializedBlocks = block.getParentStatementHolder().getStack(block).stream().map(Block::serialize).toList();
        } else {
            serializedBlocks = Collections.singletonList(block.serialize());
        }
        statementCopied.set(true);
        expressionCopied.set(false);
    }

    public static void copyExpression(ExpressionBlock block) {
        serializedBlocks = Collections.singletonList(block.serialize());
        expressionCopied.set(true);
        statementCopied.set(false);
    }

    public static ExpressionBlock pasteExpression() {
        return BlockRegistry.newExpression(serializedBlocks.get(0));
    }

    public static StatementBlock[] pasteStatement() {
        return serializedBlocks.stream().map(BlockRegistry::newStatement).toArray(StatementBlock[]::new);
    }

    public static ReadOnlyBooleanProperty statementCopiedProperty() {
        return statementCopied;
    }

    public static ReadOnlyBooleanProperty expressionCopiedProperty() {
        return expressionCopied;
    }
}
