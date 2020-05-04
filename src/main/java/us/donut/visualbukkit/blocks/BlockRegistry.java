package us.donut.visualbukkit.blocks;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {

    private static Map<Class<? extends CodeBlock>, BlockInfo<? extends CodeBlock>> blockTypes = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void registerAll() {
        Reflections reflections = new Reflections("us.donut.visualbukkit.blocks");
        for (Class<?> clazz : reflections.getSubTypesOf(CodeBlock.class)) {
            if (!Modifier.isAbstract(clazz.getModifiers()) && clazz != EmptyExpressionBlock.class) {
                Class<? extends CodeBlock> blockType = (Class<? extends CodeBlock>) clazz;
                BlockInfo<?> blockInfo = new BlockInfo<>(blockType);
                blockTypes.put(blockType, blockInfo);
                blockInfo.createBlock();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends CodeBlock> BlockInfo<T> getInfo(Class<T> blockType) {
        return (BlockInfo<T>) blockTypes.get(blockType);
    }

    @SuppressWarnings("unchecked")
    public static <T extends CodeBlock> BlockInfo<T> getInfo(T block) {
        return (BlockInfo<T>) getInfo(block.getClass());
    }

    public static Collection<BlockInfo<? extends CodeBlock>> getAll() {
        return Collections.unmodifiableCollection(blockTypes.values());
    }
}
