package us.donut.visualbukkit.blocks;

import org.apache.commons.lang.WordUtils;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.util.DataConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BlockDefinition<T extends CodeBlock> {

    private String name;
    private String description;
    private Constructor<T> constructor;

    public BlockDefinition(Class<T> clazz) throws NoSuchMethodException {
        name = clazz.isAnnotationPresent(Name.class) ?
                clazz.getAnnotation(Name.class).value() :
                WordUtils.capitalize(TypeHandler.getUserFriendlyName(clazz).replaceFirst(".+? ", ""));

        if (clazz.isAnnotationPresent(Description.class)) {
            description = String.join("\n", clazz.getAnnotation(Description.class).value());
        }

        constructor = clazz.getConstructor();
    }

    public T createBlock() {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            VisualBukkit.displayException("Failed to create block", e);
            return null;
        }
    }

    public T createBlock(DataConfig config) {
        T block = createBlock();
        block.loadFrom(config);
        return block;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
