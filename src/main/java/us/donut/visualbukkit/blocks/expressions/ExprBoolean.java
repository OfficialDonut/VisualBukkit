package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

public class ExprBoolean extends ConditionBlock {

    @Override
    protected Syntax init() {
        getStyleClass().clear();
        return new Syntax(new BinaryChoiceParameter("true", "false"));
    }

    @Override
    protected String toNonNegatedJava() {
        return "true";
    }

    @Override
    protected String toNegatedJava() {
        return "false";
    }
}
