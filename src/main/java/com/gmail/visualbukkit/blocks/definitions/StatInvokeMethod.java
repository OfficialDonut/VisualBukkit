package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.parameters.ClassElementParameter;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.reflection.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
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
                return getValue() != null ? getValue().method().getName() : null;
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
                if (!Modifier.isStatic(newValue.method().getModifiers())) {
                    Class<?> clazz = classParameter.getSelectionModel().getSelectedItem().clazz();
                    addParameter(ClassInfo.toString(clazz, false), clazz.getPackageName(), new ExpressionParameter(clazz));
                }
                for (Parameter parameter : newValue.method().getParameters()) {
                    Class<?> clazz = parameter.getType();
                    addParameter(ClassInfo.toString(clazz, false), clazz.getPackageName(), new ExpressionParameter(clazz));
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
        Method method = methodInfo.method();
        StringBuilder builder = new StringBuilder();
        builder.append(arg(Modifier.isStatic(method.getModifiers()) ? 0 : 2))
                .append(".")
                .append(method.getName())
                .append("(");
        if (method.getParameterCount() > 0) {
            StringJoiner joiner = new StringJoiner(",");
            for (int i = Modifier.isStatic(method.getModifiers()) ? 2 : 3; i < parameters.size(); i++) {
                joiner.add(arg(i));
            }
            builder.append(joiner);
        }
        builder.append(");");
        return builder.toString();
    }
}
