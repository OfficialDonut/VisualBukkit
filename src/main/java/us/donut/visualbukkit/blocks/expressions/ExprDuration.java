package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.time.Duration;

@Description({"A duration of time", "Returns: duration"})
public class ExprDuration extends ExpressionBlock<Duration> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, new ChoiceParameter("ticks", "seconds", "minutes", "hours", "days"));
    }

    @Override
    public String toJava() {
        String time = "(" + arg(0) + ")";
        switch (arg(1)) {
            case "ticks":
                time = time + "*50";
                break;
            case "seconds":
                time = time + "*1000";
                break;
            case "minutes":
                time = time + "*60*1000";
                break;
            case "hours":
                time = time + "*60*60*1000";
                break;
            case "days":
                time = time + "*24*60*60*1000";
                break;
        }
        return "java.time.Duration.ofMillis((long) (" + time + "))";
    }
}
