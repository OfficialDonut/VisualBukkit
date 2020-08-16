package us.donut.visualbukkit.blocks;

import com.google.gson.internal.Primitives;
import us.donut.visualbukkit.blocks.annotations.Modifier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ExpressionDefinition<T extends ExpressionBlock<?>> extends BlockDefinition<T> {

    private Class<?> returnType;
    private Set<ModificationType> modifiers;

    public ExpressionDefinition(Class<T> clazz) throws NoSuchMethodException {
        super(clazz);

        Class<?> c = clazz;
        while (c != null && c != ExpressionBlock.class) {
            if (c.getGenericSuperclass() instanceof ParameterizedType) {
                Type type = ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
                if (type instanceof Class) {
                    returnType = (Class<?>) type;
                    break;
                }
            }
            c = c.getSuperclass();
        }
        if (returnType == null) {
            throw new IllegalStateException("Missing return type for " + clazz.getName());
        } else if (Primitives.isWrapperType(returnType)) {
            returnType = Primitives.unwrap(returnType);
        }

        modifiers = clazz.isAnnotationPresent(Modifier.class) ?
                Arrays.stream(clazz.getAnnotation(Modifier.class).value()).collect(Collectors.toSet()) :
                Collections.emptySet();
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Set<ModificationType> getModifiers() {
        return Collections.unmodifiableSet(modifiers);
    }
}
