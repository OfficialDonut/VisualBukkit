package us.donut.visualbukkit.blocks;

public abstract class ChangeableExpressionBlock extends ExpressionBlock {

    public abstract String change(ChangeType changeType, String delta);

    public Class<?> getDeltaType(ChangeType changeType) {
        return ((ExpressionBlock) this).getReturnType();
    }
}
