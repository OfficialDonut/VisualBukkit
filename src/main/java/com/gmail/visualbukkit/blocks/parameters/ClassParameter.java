package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.PopOverSelector;

public class ClassParameter extends PopOverSelector<ClassInfo> implements BlockParameter {

    public ClassParameter() {
        super(ClassRegistry.getClasses());
        setSelectAction(clazz -> UndoManager.current().execute(() -> setValue(clazz)));
    }

    @Override
    public String generateJava() {
        return getValue() != null ? getValue().getName() : null;
    }

    @Override
    public Object serialize() {
        return generateJava();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String clazz) {
            setValue(ClassRegistry.getClass(clazz).orElseThrow(() -> new IllegalStateException("Class not registered: " + clazz)));
        }
    }
}
