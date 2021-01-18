package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.gui.NotificationManager;
import org.apache.commons.lang.WordUtils;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class BlockDefinition<T extends CodeBlock> implements Comparable<BlockDefinition<T>> {

    private Class<T> blockClass;
    private String name;
    private String description;
    private Constructor<T> constructor;

    public BlockDefinition(Class<T> clazz) throws NoSuchMethodException {
        this.blockClass = clazz;
        constructor = clazz.getConstructor();

        name = clazz.isAnnotationPresent(Name.class) ?
                clazz.getAnnotation(Name.class).value() :
                WordUtils.capitalize(TypeHandler.getUserFriendlyName(clazz).replaceFirst(".+? ", ""));

        description = clazz.isAnnotationPresent(Description.class) ?
                clazz.getAnnotation(Description.class).value() :
                "No description provided";
    }

    public T createBlock(JSONObject obj) {
        try {
            T block = constructor.newInstance();
            if (obj != null) {
                block.deserialize(obj);
            }
            return block;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            NotificationManager.displayException("Failed to create block", e);
            return null;
        }
    }

    @Override
    public int compareTo(BlockDefinition<T> def) {
        return name.compareTo(def.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public Class<T> getBlockClass() {
        return blockClass;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
