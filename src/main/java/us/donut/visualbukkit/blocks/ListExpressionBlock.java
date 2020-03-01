package us.donut.visualbukkit.blocks;

public abstract class ListExpressionBlock extends ExpressionBlock implements ExpressionBlock.Changeable {

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case DELETE: return toJava() + ".clear();";
            case ADD: return toJava() + ".add(" + delta + ");";
            case REMOVE: return toJava() + ".remove(" + delta + ");";
            default: return null;
        }
    }
}
