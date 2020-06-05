package us.donut.visualbukkit.blocks;

import com.google.gson.internal.Primitives;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.lang.WordUtils;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.annotations.*;
import us.donut.visualbukkit.plugin.modules.PluginModule;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class BlockInfo<T extends CodeBlock> {

    private Class<T> blockType;
    private Constructor<T> constructor;
    private String name;
    private String description;
    private String[] categories;
    private Class<?>[] events;
    private PluginModule[] modules;
    private CtMethod[] utilMethods;
    private Class<?> returnType;

    public BlockInfo(Class<T> blockType) {
        this.blockType = blockType;

        try {
            constructor = blockType.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No constructor for " + blockType.getCanonicalName(), e);
        }

        name = blockType.isAnnotationPresent(Name.class) ?
                blockType.getAnnotation(Name.class).value() :
                WordUtils.capitalize(TypeHandler.getUserFriendlyName(blockType).replaceFirst(".+? ", ""));

        if (blockType.isAnnotationPresent(Description.class)) {
            description = String.join("\n", blockType.getAnnotation(Description.class).value());
        }

        if (blockType.isAnnotationPresent(Category.class)) {
            categories = blockType.getAnnotation(Category.class).value();
        }

        if (blockType.isAnnotationPresent(Event.class)) {
            events = blockType.getAnnotation(Event.class).value();
        }

        if (blockType.isAnnotationPresent(Module.class)) {
            modules = blockType.getAnnotation(Module.class).value();
        }

        List<Method> methods = new ArrayList<>();
        for (Method method : blockType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(UtilMethod.class)) {
                methods.add(method);
            }
        }
        if (!methods.isEmpty()) {
            try {
                CtClass ctClass = ClassPool.getDefault().get(blockType.getCanonicalName());
                utilMethods = new CtMethod[methods.size()];
                for (int i = 0; i < methods.size(); i++) {
                    utilMethods[i] = ctClass.getDeclaredMethod(methods.get(i).getName());
                }
            } catch (NotFoundException e) {
                VisualBukkit.displayException("Failed to register util method", e);
            }
        }

        if (ExpressionBlock.class.isAssignableFrom(blockType)) {
            Class<?> clazz = blockType;
            while (clazz != null && clazz != ExpressionBlock.class) {
                if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                    Type type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
                    if (type instanceof Class) {
                        returnType = (Class<?>) type;
                        break;
                    }
                }
                clazz = clazz.getSuperclass();
            }
            if (returnType != null) {
                if (Primitives.isWrapperType(returnType)) {
                    returnType = Primitives.unwrap(returnType);
                }
            } else {
                throw new IllegalStateException("Missing return type for " + blockType.getCanonicalName());
            }
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

    public Class<T> getBlockType() {
        return blockType;
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

    public PluginModule[] getModules() {
        return modules;
    }

    public CtMethod[] getUtilMethods() {
        return utilMethods;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public class Node extends Label {

        public Node() {
            getStyleClass().add(StatementBlock.class.isAssignableFrom(blockType) ? "statement-block-info-node" : "expression-block-info-node");
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
