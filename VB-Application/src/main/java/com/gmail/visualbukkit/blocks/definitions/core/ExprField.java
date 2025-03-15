package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.FieldParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.FieldInfo;

@BlockDefinition(id = "expr-field", name = "Field", description = "A field of a class")
public class ExprField extends ExpressionBlock {

    private final ClassParameter classParameter;
    private final FieldParameter fieldParameter;

    public ExprField() {
        addParameter("Class", classParameter = new ClassParameter(c -> !c.getFields().isEmpty()));
        addParameter("Field", fieldParameter = new FieldParameter(this, classParameter));
    }

    @Override
    public ClassInfo getReturnType() {
        return fieldParameter.getValue() != null ? fieldParameter.getValue().getType() : ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        ClassInfo classInfo = classParameter.getValue();
        FieldInfo fieldInfo = fieldParameter.getValue();
        if (classInfo == null || fieldInfo == null) {
            return "((Object) null)";
        }
        return arg(fieldInfo.isStatic() ? 0 : 2, buildInfo) + "." + fieldInfo.getName();
    }

    public ClassParameter getClassParameter() {
        return classParameter;
    }

    public FieldParameter getFieldParameter() {
        return fieldParameter;
    }
}
