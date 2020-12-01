package com.gmail.visualbukkit.blocks;

import com.google.gson.internal.Primitives;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ExpressionDefinition<T extends ExpressionBlock<?>> extends BlockDefinition<T> {

    private Class<?> returnType;

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
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}
