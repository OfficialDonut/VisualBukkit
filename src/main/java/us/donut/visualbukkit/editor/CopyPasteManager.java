package us.donut.visualbukkit.editor;

import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.util.DataConfig;

public class CopyPasteManager {

    private static BlockDefinition<?> copied;
    private static DataConfig config;

    public static void copy(CodeBlock block) {
        copied = block instanceof StatementBlock ?
                BlockRegistry.getStatement(((StatementBlock) block).getClass()) :
                BlockRegistry.getExpression(((ExpressionBlock<?>) block).getClass());
        config = new DataConfig();
        block.saveTo(config);
    }

    public static CodeBlock paste() {
        return copied != null ? copied.createBlock(config) : null;
    }

    public static BlockDefinition<?> getCopied() {
        return copied;
    }
}
