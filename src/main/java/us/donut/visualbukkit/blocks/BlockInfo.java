package us.donut.visualbukkit.blocks;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.apache.commons.lang.WordUtils;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Name;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BlockInfo<T extends CodeBlock> {

    private Class<T> type;
    private Constructor<T> constructor;
    private String name;
    private String description;
    private String[] categories;
    private Class<?>[] events;

    public BlockInfo(Class<T> type) {
        this.type = type;

        try {
            constructor = type.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No constructor for " + type.getCanonicalName(), e);
        }

        name = type.isAnnotationPresent(Name.class) ?
                type.getAnnotation(Name.class).value() :
                WordUtils.capitalize(TypeHandler.getUserFriendlyName(type).replaceFirst(".+? ", ""));

        if (type.isAnnotationPresent(Description.class)) {
            description = String.join("\n", type.getAnnotation(Description.class).value());
        }

        if (type.isAnnotationPresent(Category.class)) {
            categories = type.getAnnotation(Category.class).value();
        }

        if (type.isAnnotationPresent(Event.class)) {
            events = type.getAnnotation(Event.class).value();
        }
    }

    public Node createNode() {
        return new Node();
    }

    public T createBlock() {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Block could not be instantiated", e);
        }
    }

    public Class<T> getType() {
        return type;
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getCategories() {
        return categories;
    }

    public Class<?>[] getEvents() {
        return events;
    }

    public class Node extends Label {

        public Node() {
            getStyleClass().add("block-info-node");
            setText(getName());
            DragManager.enableDragging(this);
            if (description != null) {
                setTooltip(new Tooltip(description));
            }
        }

        public BlockInfo<T> getBlockInfo() {
            return BlockInfo.this;
        }
    }
}
