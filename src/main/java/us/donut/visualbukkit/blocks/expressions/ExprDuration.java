package us.donut.visualbukkit.blocks.expressions;

import org.apache.commons.lang.WordUtils;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Description({"A duration of time", "Returns: duration"})
public class ExprDuration extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(long.class, new ChoiceParameter("ticks", "seconds", "minutes", "hours", "days"));
    }

    @Override
    public String toJava() {
        String unit = arg(1);
        return unit.equals("ticks") ?
                "java.time.Duration.ofMillis(" + arg(0) + "*50)" :
                "java.time.Duration.of" + WordUtils.capitalize(unit) + "(" + arg(0) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return Duration.class;
    }
}
