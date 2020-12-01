package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.gui.NotificationManager;
import org.apache.commons.lang.WordUtils;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BlockDefinition<T extends CodeBlock> implements Comparable<BlockDefinition<T>> {

    private Class<T> blockClass;
    private String name;
    private String description;
    private Set<String> categories;
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

        categories = clazz.isAnnotationPresent(Category.class) ?
                Arrays.stream(clazz.getAnnotation(Category.class).value()).collect(Collectors.toSet()) :
                new HashSet<>(1);
        categories.add(Category.STATEMENTS);
    }

    public T createBlock() {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            NotificationManager.displayException("Failed to create block", e);
            return null;
        }
    }

    public T createBlock(JSONObject obj) {
        T block = createBlock();
        block.deserialize(obj);
        return block;
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

    public Set<String> getCategories() {
        return categories;
    }
}
