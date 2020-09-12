package us.donut.visualbukkit.blocks.expressions;

import org.apache.commons.lang.WordUtils;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.Duration;

@Description({"A duration of time", "Returns: duration"})
public class ExprDuration extends ExpressionBlock<Duration> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, new ChoiceParameter("ticks", "seconds", "minutes", "hours", "days"));
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.DURATION);
        return "Duration.from" + WordUtils.capitalize(arg(1)) + "(" + arg(0) + ")";
    }
}
