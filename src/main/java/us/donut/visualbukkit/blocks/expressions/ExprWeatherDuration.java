package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The remaining time in ticks of the current weather conditions", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprWeatherDuration extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("weather duration in", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWeatherDuration()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return arg(0) + ".setWeatherDuration(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
