package com.gmail.visualbukkit.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class LoadedFieldInfo extends FieldInfo {

    private final Field field;

    protected LoadedFieldInfo(Field field) {
        this.field = field;
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public ClassInfo getType() {
        return ClassInfo.of(field.getType());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }
}
