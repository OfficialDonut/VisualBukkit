package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.FieldInfo;

import java.util.Collection;
import java.util.function.Predicate;

public class FieldParameter extends ClassElementParameter<FieldInfo> {

    private final Block block;
    private final Predicate<FieldInfo> filter;

    public FieldParameter(Block block, ClassParameter classParameter) {
        this(block, classParameter, null);
    }

    public FieldParameter(Block block, ClassParameter classParameter, Predicate<FieldInfo> filter) {
        super("pinned-fields", classParameter);
        this.block = block;
        this.filter = filter;

        valueProperty().addListener((observable, oldValue, newValue) -> {
            block.removeParameters(2);
            if (newValue != null) {
                if (!newValue.isStatic()) {
                    ClassInfo clazz = classParameter.getSelectionModel().getSelectedItem();
                    block.addParameter(clazz.getSimpleName(), clazz.getName(), new ExpressionParameter(clazz));
                }
            }
        });
    }

    @Override
    public Collection<FieldInfo> generateItems(ClassInfo classInfo) {
        block.removeParameters(2);
        return filter != null ? classInfo.getFields(filter) : classInfo.getFields();
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return getValue() != null ? getValue().getName() : null;
    }

    @Override
    public Object serialize() {
        return getValue() != null ? getValue().getName() : null;
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String s) {
            for (FieldInfo field : getItemList()) {
                if (field.getName().equals(s)) {
                    setValue(field);
                    return;
                }
            }
        }
    }
}
