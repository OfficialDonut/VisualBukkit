package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class EnumBlock<T extends Enum<?>> extends ExpressionBlock<T> {

    private static Map<Class<?>, String[]> constants = new HashMap<>();

    @Override
    protected final SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(constants.computeIfAbsent(getClass(), key -> computeConstants())));
    }

    protected String[] computeConstants() {
        return Arrays.stream(getEnum().getEnumConstants())
                .map(Enum::name)
                .sorted()
                .toArray(String[]::new);
    }

    @Override
    public final String toJava() {
        return getEnum().getCanonicalName() + "." + arg(0);
    }

    @SuppressWarnings("unchecked")
    public final Class<? extends Enum<?>> getEnum() {
        return (Class<? extends Enum<?>>) getReturnType();
    }
}
