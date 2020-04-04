package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;

public abstract class ConditionBlock extends ExpressionBlock<Boolean> {

    protected abstract String toNonNegatedJava();

    protected String toNegatedJava() {
        return "!" + toNonNegatedJava();
    }

    @Override
    public final String toJava() {
        return isNegated() ? toNegatedJava() : toNonNegatedJava();
    }

    protected boolean isNegated() {
        for (BlockParameter parameter : getSyntaxNode().getParameters()) {
            if (parameter instanceof ChoiceParameter) {
                return ((ChoiceParameter) parameter).getComboBox().getSelectionModel().getSelectedIndex() != 0;
            }
        }
        throw new IllegalStateException("No choice parameter found");
    }
}
