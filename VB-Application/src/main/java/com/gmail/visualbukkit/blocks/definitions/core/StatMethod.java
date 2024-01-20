package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.MethodParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.MethodInfo;

import java.util.StringJoiner;

@BlockDefinition(id = "stat-method", name = "Method", description = "Invokes the given method")
public class StatMethod extends StatementBlock {

    private final ClassParameter classParameter;
    private final MethodParameter methodParameter;

    public StatMethod() {
        addParameter("Class", classParameter = new ClassParameter());
        addParameter("Method", methodParameter = new MethodParameter(this, classParameter));
    }

    public StatMethod(ClassInfo clazz, MethodInfo method, ExpressionBlock... parameterExpressions) {
        this();
        classParameter.setValue(clazz);
        methodParameter.setValue(method);
        for (int i = 0; i < parameterExpressions.length; i++) {
            ((ExpressionParameter) parameters.get(i + 2)).setExpression(parameterExpressions[i]);
        }
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        ClassInfo classInfo = classParameter.getValue();
        MethodInfo methodInfo = methodParameter.getValue();
        if (classInfo == null || methodInfo == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(arg(methodInfo.isStatic() ? 0 : 2, buildInfo))
                .append(".")
                .append(methodInfo.getName())
                .append("(");
        if (!methodInfo.getParameters().isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            for (int i = methodInfo.isStatic() ? 2 : 3; i < parameters.size(); i++) {
                joiner.add(arg(i, buildInfo));
            }
            builder.append(joiner);
        }
        builder.append(");");
        return builder.toString();
    }
}
