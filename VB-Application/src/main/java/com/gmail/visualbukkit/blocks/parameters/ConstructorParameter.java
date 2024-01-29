package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.ConstructorInfo;
import com.gmail.visualbukkit.reflection.ParameterInfo;

import java.util.Collection;
import java.util.function.Predicate;

public class ConstructorParameter extends ClassElementParameter<ConstructorInfo> {

    private final Block block;
    private final Predicate<ConstructorInfo> filter;

    public ConstructorParameter(Block block, ClassParameter classParameter) {
        this(block, classParameter, null);
    }

    public ConstructorParameter(Block block, ClassParameter classParameter, Predicate<ConstructorInfo> filter) {
        super("pinned-constructors", classParameter);
        this.block = block;
        this.filter = filter;

        valueProperty().addListener((observable, oldValue, newValue) -> {
            block.removeParameters(2);
            if (newValue != null) {
                for (ParameterInfo parameter : newValue.getParameters()) {
                    ClassInfo clazz = parameter.getType();
                    block.addParameter(parameter.getName(), clazz.getName(), new ExpressionParameter(clazz));
                }
            }
        });
    }

    @Override
    public Collection<ConstructorInfo> generateItems(ClassInfo classInfo) {
        block.removeParameters(2);
        return filter != null ? classInfo.getConstructors(filter) : classInfo.getConstructors();
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "<init>";
    }

    @Override
    public Object serialize() {
        return getValue() != null ? getValue().getSignature() : null;
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String s) {
            for (ConstructorInfo constructor : getItemList()) {
                if (constructor.getSignature().equals(s)) {
                    setValue(constructor);
                    return;
                }
            }
            throw new IllegalStateException();
        }
    }
}
