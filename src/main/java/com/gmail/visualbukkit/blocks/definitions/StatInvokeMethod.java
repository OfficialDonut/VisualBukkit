package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.parameters.ClassElementParameter;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.classes.ClassInfo;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.classes.MethodInfo;
import com.gmail.visualbukkit.blocks.classes.ParameterInfo;

import java.util.Collection;
import java.util.StringJoiner;

@BlockDefinition(uid = "stat-invoke-method", name = "Invoke Method")
public class StatInvokeMethod extends StatementBlock {

    private final ClassParameter classParameter;
    private final ClassElementParameter<MethodInfo> methodParameter;

    public StatInvokeMethod() {
        classParameter = new ClassParameter();
        methodParameter = new ClassElementParameter<>(classParameter) {
            @Override
            public Collection<MethodInfo> generateItems(ClassInfo classInfo) {
                removeParameters(2);
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
        };

        methodParameter.valueProperty().addListener((observable, oldValue, newValue) -> {
            removeParameters(2);
            if (newValue != null) {
                if (!newValue.isStatic()) {
                    ClassInfo clazz = classParameter.getSelectionModel().getSelectedItem();
                    addParameter(clazz.getSimpleName(), clazz.getName(), new ExpressionParameter(clazz));
                }
                for (ParameterInfo parameter : newValue.getParameters()) {
                    ClassInfo clazz = parameter.getType();
                    addParameter(parameter.getName(), clazz.getName(), new ExpressionParameter(clazz));
                }
            }
        });

        addParameter("Class", classParameter);
        addParameter("Method", methodParameter);
    }

    @Override
    public String generateJava() {
        ClassInfo classInfo = classParameter.getValue();
        MethodInfo methodInfo = methodParameter.getValue();
        if (classInfo == null || methodInfo == null) {
            return "";
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
        builder.append(");");
        return builder.toString();
    }
}
