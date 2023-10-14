package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.PopOverSelector;

public class ClassParameter extends PopOverSelector<ClassInfo> implements BlockParameter {

    public ClassParameter() {
        super(ClassRegistry.getClasses());
    }

    @Override
    public String generateJava() {
        return getValue() != null ? getValue().clazz().getCanonicalName() : null;
    }

    @Override
    public Object serialize() {
        return generateJava();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String clazz) {
            ClassInfo classInfo = ClassRegistry.getClass(clazz);
            if (classInfo == null) {
                throw new IllegalStateException("Class not registered: " + clazz);
            }
            setValue(classInfo);
        }
    }
}
