package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;

public abstract class ConditionBlock extends ExpressionBlock {

    @Override
    public final Class<?> getReturnType() {
        return boolean.class;
    }

    protected boolean isNegated() {
        for (BlockParameter parameter : getSyntaxNode().getParameters()) {
            if (parameter instanceof ChoiceParameter) {
                return ((ChoiceParameter) parameter).getSelectionModel().getSelectedIndex() != 0;
            }
        }
        throw new IllegalStateException("No choice parameter found");
    }
}
