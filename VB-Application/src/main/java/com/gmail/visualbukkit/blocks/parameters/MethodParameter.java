package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.MethodInfo;
import com.gmail.visualbukkit.reflection.ParameterInfo;

import java.util.Collection;
import java.util.function.Predicate;

public class MethodParameter extends ClassElementParameter<MethodInfo> {

    private final Block block;
    private final Predicate<MethodInfo> filter;

    public MethodParameter(Block block, ClassParameter classParameter) {
        this(block, classParameter, null);
    }

    public MethodParameter(Block block, ClassParameter classParameter, Predicate<MethodInfo> filter) {
        super("pinned-methods", classParameter);
        this.block = block;
        this.filter = filter;

        valueProperty().addListener((observable, oldValue, newValue) -> {
            block.removeParameters(2);
            if (newValue != null) {
                if (!newValue.isStatic()) {
                    ClassInfo clazz = classParameter.getSelectionModel().getSelectedItem();
                    block.addParameter(clazz.getSimpleName(), clazz.getName(), new ExpressionParameter(clazz));
                }
                for (ParameterInfo parameter : newValue.getParameters()) {
                    ClassInfo clazz = parameter.getType();
                    block.addParameter(parameter.getName(), clazz.getName(), new ExpressionParameter(clazz));
                }
            }
        });
    }

    @Override
    public Collection<MethodInfo> generateItems(ClassInfo classInfo) {
        block.removeParameters(2);
        return filter != null ? classInfo.getMethods(filter) : classInfo.getMethods();
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return getValue() != null ? getValue().getName() : null;
    }

    @Override
    public Object serialize() {
        return getValue() != null ? getValue().getSignature() : null;
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String s) {
            for (MethodInfo method : getItemList()) {
                if (method.getSignature().equals(s)) {
                    setValue(method);
                    return;
                }
            }
            throw new IllegalStateException();
        }
    }
}
