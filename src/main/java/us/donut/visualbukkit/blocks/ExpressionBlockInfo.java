package us.donut.visualbukkit.blocks;

import com.google.gson.internal.Primitives;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.statements.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ExpressionBlockInfo<T extends ExpressionBlock<?>> extends BlockInfo<T> {

    private Class<?> returnType;
    private Class<? extends ModifierBlock>[] modifiers;

    public ExpressionBlockInfo(Class<T> blockType) {
        super(blockType);

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

        if (blockType.isAnnotationPresent(Modifier.class)) {
            Set<Class<?>> modifiers = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
            for (ModificationType modificationType : blockType.getAnnotation(Modifier.class).value()) {
                Class<?> modifierBlockClass = null;
                switch (modificationType) {
                    case SET:
                        modifierBlockClass = StatSet.class;
                        break;
                    case ADD:
                        modifierBlockClass = StatAdd.class;
                        break;
                    case REMOVE:
                        modifierBlockClass = StatRemove.class;
                        break;
                    case DELETE:
                        modifierBlockClass = StatDelete.class;
                        break;
                    case CLEAR:
                        modifierBlockClass = StatClear.class;
                        break;
                }
                modifiers.add(modifierBlockClass);
            }
            this.modifiers = modifiers.toArray(new Class[0]);
        }
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<? extends ModifierBlock>[] getModifiers() {
        return modifiers;
    }
}
