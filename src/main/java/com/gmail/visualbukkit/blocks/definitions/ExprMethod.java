package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.MethodParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.MethodInfo;

import java.util.StringJoiner;

@BlockDefinition(uid = "expr-method", name = "Method")
public class ExprMethod extends ExpressionBlock {

    private final ClassParameter classParameter;
    private final MethodParameter methodParameter;

    public ExprMethod() {
        addParameter("Class", classParameter = new ClassParameter());
        addParameter("Method", methodParameter = new MethodParameter(this, classParameter, m -> m.getReturnType() != null));
    }

    @Override
    public ClassInfo getReturnType() {
        return methodParameter.getValue() != null ? methodParameter.getValue().getReturnType() : ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava() {
        ClassInfo classInfo = classParameter.getValue();
        MethodInfo methodInfo = methodParameter.getValue();
        if (classInfo == null || methodInfo == null) {
            return "((Object) null)";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(arg(methodInfo.isStatic() ? 0 : 2))
                .append(".")
                .append(methodInfo.getName())
                .append("(");
        if (!methodInfo.getParameters().isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            for (int i = methodInfo.isStatic() ? 2 : 3; i < parameters.size(); i++) {
                joiner.add(arg(i));
            }
            builder.append(joiner);
        }
        builder.append(")");
        return builder.toString();
    }
}
