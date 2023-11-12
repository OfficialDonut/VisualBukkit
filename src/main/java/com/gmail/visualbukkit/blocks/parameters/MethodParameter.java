package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.MethodInfo;
import com.gmail.visualbukkit.reflection.ParameterInfo;

import java.util.Collection;

public class MethodParameter extends ClassElementParameter<MethodInfo> {

    private final Block block;

    public MethodParameter(Block block, ClassParameter classParameter) {
        super(classParameter);
        this.block = block;

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
        return classInfo.getMethods();
    }

    @Override
    public String generateJava() {
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
        }
    }
}
