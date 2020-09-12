package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.weather.WeatherChangeEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The new weather state in a WeatherChangeEvent", "Returns: boolean"})
public class ExprEventWeatherState extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("event weather state");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(WeatherChangeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.toWeatherState()";
    }
}
