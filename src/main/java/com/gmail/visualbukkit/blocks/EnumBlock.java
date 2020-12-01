package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.components.ChoiceParameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class EnumBlock<T extends Enum<?>> extends ExpressionBlock<T> {

    private static Map<Class<?>, String[]> constants = new HashMap<>();
    private Class<T> enumClass;

    @SuppressWarnings("unchecked")
    public EnumBlock() {
        enumClass = (Class<T>) getDefinition().getReturnType();
        init(new ChoiceParameter(constants.computeIfAbsent(getClass(), k -> computeConstants())));
    }

    protected String[] computeConstants() {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .sorted()
                .toArray(String[]::new);
    }

    @Override
    public final String toJava() {
        return enumClass.getCanonicalName() + "." + arg(0);
    }
}
