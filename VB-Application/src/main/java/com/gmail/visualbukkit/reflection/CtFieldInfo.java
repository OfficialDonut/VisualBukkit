package com.gmail.visualbukkit.reflection;

import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

public class CtFieldInfo extends FieldInfo {

    private final CtField field;

    public CtFieldInfo(CtField field) {
        this.field = field;
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public ClassInfo getType() {
        try {
            return ClassInfo.of(field.getType().getName());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }
}
