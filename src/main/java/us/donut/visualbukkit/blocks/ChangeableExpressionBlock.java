package us.donut.visualbukkit.blocks;

public abstract class ChangeableExpressionBlock<T> extends ExpressionBlock<T> {

    public abstract String change(ChangeType changeType, String delta);

    public Class<?> getDeltaType(ChangeType changeType) {
        return getReturnType();
    }
}
