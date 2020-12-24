package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.weather.WeatherChangeEvent;

@Description("The new weather state in a WeatherChangeEvent")
public class ExprEventWeatherState extends ExpressionBlock<Boolean> {

    public ExprEventWeatherState() {
        init("weather state");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Weather state must be used in a WeatherChangeEvent", WeatherChangeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.toWeatherState()";
    }
}