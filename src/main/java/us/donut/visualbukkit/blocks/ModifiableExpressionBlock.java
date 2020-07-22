package us.donut.visualbukkit.blocks;

public abstract class ModifiableExpressionBlock<T> extends ExpressionBlock<T> {

    public abstract String modify(ModificationType modificationType, String delta);

    public Class<?> getDeltaType(ModificationType modificationType) {
        return getReturnType();
    }
}
