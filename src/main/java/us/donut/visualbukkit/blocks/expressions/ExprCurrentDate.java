package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.time.LocalDateTime;

@Description({"The current date", "Returns: date"})
public class ExprCurrentDate extends ExpressionBlock<LocalDateTime> {

    @Override
    protected Syntax init() {
        return new Syntax("current date");
    }

    @Override
    public String toJava() {
        return "java.time.LocalDateTime.now()";
    }
}
