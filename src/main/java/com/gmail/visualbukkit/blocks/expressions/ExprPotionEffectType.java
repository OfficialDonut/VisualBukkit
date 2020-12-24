package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.potion.PotionEffectType;

@Description("A potion effect type")
public class ExprPotionEffectType extends ExpressionBlock<PotionEffectType> {

    private static String[] types = {
            "ABSORPTION", "BAD_OMEN", "BLINDNESS", "CONDUIT_POWER", "CONFUSION", "DAMAGE_RESISTANCE", "DOLPHINS_GRACE",
            "FAST_DIGGING", "FIRE_RESISTANCE", "GLOWING", "HARM", "HEAL", "HEALTH_BOOST", "HERO_OF_THE_VILLAGE",
            "HUNGER", "INCREASE_DAMAGE", "INVISIBILITY", "JUMP", "LEVITATION", "LUCK", "NIGHT_VISION", "POISON",
            "REGENERATION", "SATURATION", "SLOW", "SLOW_DIGGING", "SLOW_FALLING", "SPEED", "UNLUCK", "WATER_BREATHING",
            "WEAKNESS", "WITHER"};

    public ExprPotionEffectType() {
        init(new ChoiceParameter(types));
    }

    @Override
    public String toJava() {
        return PotionEffectType.class.getCanonicalName() + "." + arg(0);
    }
}