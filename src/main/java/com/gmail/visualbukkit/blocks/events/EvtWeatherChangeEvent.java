package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EvtWeatherChangeEvent extends EventBlock {

    public EvtWeatherChangeEvent() {
        super(WeatherChangeEvent.class);
    }
}