package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.ConstructorParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.ConstructorInfo;

import java.util.StringJoiner;

@BlockDefinition(id = "expr-new-object", name = "New Object", description = "Creates a new instance of a class")
public class ExprNewObject extends ExpressionBlock {

    private final ClassParameter classParameter;
    private final ConstructorParameter constructorParameter;

    public ExprNewObject() {
        addParameter("Class", classParameter = new ClassParameter(c -> !c.getConstructors().isEmpty()));
        addParameter("Constructor", constructorParameter = new ConstructorParameter(this, classParameter));
    }

    public ExprNewObject(ClassInfo clazz, ConstructorInfo constructor, ExpressionBlock... parameterExpressions) {
        this();
        classParameter.setValue(clazz);
        constructorParameter.setValue(constructor);
        for (int i = 0; i < parameterExpressions.length; i++) {
            ((ExpressionParameter) parameters.get(i + 2)).setExpression(parameterExpressions[i]);
        }
    }

    @Override
    public ClassInfo getReturnType() {
        return classParameter.getValue() != null ? classParameter.getValue() : ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        ClassInfo classInfo = classParameter.getValue();
        ConstructorInfo constructorInfo = constructorParameter.getValue();
        if (classInfo == null || constructorInfo == null) {
            return "((Object) null)";
        }
        StringBuilder builder = new StringBuilder()
                .append("new ")
                .append(classInfo.getName())
                .append("(");
        if (!constructorInfo.getParameters().isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            for (int i = 2; i < parameters.size(); i++) {
                joiner.add(arg(i, buildInfo));
            }
            builder.append(joiner);
        }
        builder.append(")");
        return builder.toString();
    }
}
