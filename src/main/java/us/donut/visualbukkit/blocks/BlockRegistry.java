package us.donut.visualbukkit.blocks;

import com.google.common.reflect.ClassPath;
import javafx.application.Platform;
import us.donut.visualbukkit.VisualBukkit;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {

    private static Map<Class<? extends CodeBlock>, BlockInfo<? extends CodeBlock>> blockTypes = new HashMap<>();

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    public static void registerAll() {
        try {
            ClassPath classPath = ClassPath.from(BlockRegistry.class.getClassLoader());
            String blockPackage = "us.donut.visualbukkit.blocks";
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(blockPackage)) {
                Class<?> clazz = classInfo.load();
                if (CodeBlock.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers()) && clazz != EmptyExpressionBlock.class) {
                    Class<? extends CodeBlock> blockType = (Class<? extends CodeBlock>) clazz;
                    BlockInfo<?> blockInfo = new BlockInfo<>(blockType);
                    blockTypes.put(blockType, blockInfo);
                    blockInfo.createBlock();
                }
            }
        } catch (IOException e) {
            VisualBukkit.displayException("Failed to register blocks", e);
            Platform.exit();
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
