package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;

public abstract class ConditionBlock extends ExpressionBlock<Boolean> {

    private BinaryChoiceParameter choiceParameter;

    protected abstract String toNonNegatedJava();

    protected String toNegatedJava() {
        return "!" + toNonNegatedJava();
    }

    @Override
    public final String toJava() {
        return isNegated() ? toNegatedJava() : toNonNegatedJava();
    }

    protected boolean isNegated() {
        if (choiceParameter == null) {
            findChoiceParameter();
        }
        return choiceParameter.isSecond();
    }

    private void findChoiceParameter() {
        for (BlockParameter parameter : getParameters()) {
            if (parameter instanceof BinaryChoiceParameter) {
                choiceParameter = (BinaryChoiceParameter) parameter;
                return;
            }
        }
        throw new IllegalStateException("No choice parameter found");
    }
}
