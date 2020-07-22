package us.donut.visualbukkit.blocks;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

public class BlockRegistry {

    private static Map<String, BlockInfo<? extends CodeBlock>> blockTypes = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void registerAll() {
        Set<Class<? extends CodeBlock>> blockTypes = new TreeSet<>((c1, c2) -> c1.equals(c2) ? 0 : StatementBlock.class.isAssignableFrom(c1) ? -1 : 1);
        blockTypes.addAll(new Reflections("us.donut.visualbukkit.blocks").getSubTypesOf(CodeBlock.class));
        for (Class<? extends CodeBlock> blockType : blockTypes) {
            if (!Modifier.isAbstract(blockType.getModifiers()) && blockType != EmptyExpressionBlock.class) {
                BlockInfo<?> blockInfo = ExpressionBlock.class.isAssignableFrom(blockType) ?
                        new ExpressionBlockInfo<>((Class<? extends ExpressionBlock<?>>) blockType) :
                        new BlockInfo<>(blockType);
                BlockRegistry.blockTypes.put(blockType.getCanonicalName(), blockInfo);
                blockInfo.createBlock();
            }
        }
    }

    public static BlockInfo<? extends CodeBlock> getInfo(String blockType) {
        return blockTypes.get(blockType);
    }

    @SuppressWarnings("unchecked")
    public static <T extends CodeBlock> BlockInfo<T> getInfo(Class<T> blockType) {
        return (BlockInfo<T>) getInfo(blockType.getCanonicalName());
    }

    @SuppressWarnings("unchecked")
    public static <T extends CodeBlock> BlockInfo<T> getInfo(T block) {
        return (BlockInfo<T>) getInfo(block.getClass());
    }

    public static Collection<BlockInfo<? extends CodeBlock>> getAll() {
        return Collections.unmodifiableCollection(blockTypes.values());
    }
}
