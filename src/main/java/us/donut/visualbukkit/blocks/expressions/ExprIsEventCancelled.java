package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if an event is cancelled", "Returns: boolean"})
public class ExprIsEventCancelled extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax("event", new ChoiceParameter("is", "is not"), "cancelled");
    }

    @Override
    protected String toNonNegatedJava() {
        return "event.isCancelled()";
    }
}
