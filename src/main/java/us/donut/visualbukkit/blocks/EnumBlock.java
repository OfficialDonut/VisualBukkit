package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.HashMap;
import java.util.Map;

public abstract class EnumBlock<T extends Enum<?>> extends ExpressionBlock<T> {

    private static Map<Class<? extends Enum<?>>, String[]> constants = new HashMap<>();

    @Override
    protected final SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(constants.computeIfAbsent(getEnum(), key -> computeConstants())));
    }

    protected String[] computeConstants() {
        Enum<?>[] enumConstants = getEnum().getEnumConstants();
        String[] stringConstants = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            stringConstants[i] = enumConstants[i].name();
        }
        return stringConstants;
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
