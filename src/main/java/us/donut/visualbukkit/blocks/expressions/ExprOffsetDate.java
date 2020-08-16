package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.time.Duration;
import java.time.LocalDateTime;

@Description({"Adds/subtracts a duration from a date", "Returns: date"})
public class ExprOffsetDate extends ExpressionBlock<LocalDateTime> {

    @Override
    protected Syntax init() {
        return new Syntax(LocalDateTime.class, new ChoiceParameter("plus", "minus"), Duration.class);
    }

    @Override
    public String toJava() {
        return arg(0) + "." + arg(1) + "(" + arg(2) + ")";
    }
}
