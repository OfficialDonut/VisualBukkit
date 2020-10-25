package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.potion.PotionEffectType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A potion effect type", "Returns: potion effect type"})
public class ExprPotionEffectType extends ExpressionBlock<PotionEffectType> {

    private static String[] types = {
            "ABSORPTION", "BAD_OMEN", "BLINDNESS", "CONDUIT_POWER", "CONFUSION", "DAMAGE_RESISTANCE", "DOLPHINS_GRACE",
            "FAST_DIGGING", "FIRE_RESISTANCE", "GLOWING", "HARM", "HEAL", "HEALTH_BOOST", "HERO_OF_THE_VILLAGE",
            "HUNGER", "INCREASE_DAMAGE", "INVISIBILITY", "JUMP", "LEVITATION", "LUCK", "NIGHT_VISION", "POISON",
            "REGENERATION", "SATURATION", "SLOW", "SLOW_DIGGING", "SLOW_FALLING", "SPEED", "UNLUCK", "WATER_BREATHING",
            "WEAKNESS", "WITHER"};

    @Override
    protected Syntax init() {
        getStyleClass().clear();
        return new Syntax(new ChoiceParameter(types));
    }

    @Override
    public String toJava() {
        return PotionEffectType.class.getCanonicalName() + "." + arg(0);
    }
}
