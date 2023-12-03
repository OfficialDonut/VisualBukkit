package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.PopOverSelector;

import java.util.Collection;
import java.util.function.Predicate;

public class ClassParameter extends PopOverSelector<ClassInfo> implements BlockParameter {

    public ClassParameter() {
        this(ClassRegistry.getClasses());
    }

    public ClassParameter(Predicate<ClassInfo> filter) {
        this(ClassRegistry.getClasses(filter));
    }

    public ClassParameter(Collection<ClassInfo> classes) {
        super(classes);
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
