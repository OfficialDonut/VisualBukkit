package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class EnumBlock<T extends Enum<?>> extends ExpressionBlock<T> {

    private static Map<Class<?>, String[]> constants = new HashMap<>();
    private Class<T> enumClass;

    @Override
    @SuppressWarnings("unchecked")
    protected Syntax init() {
        getStyleClass().clear();
        enumClass = (Class<T>) getReturnType();
        return new Syntax(new ChoiceParameter(constants.computeIfAbsent(getClass(), k -> computeConstants())));
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
