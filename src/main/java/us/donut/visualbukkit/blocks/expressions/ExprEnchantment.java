package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.enchantments.Enchantment;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An enchantment", "Returns: enchantment"})
public class ExprEnchantment extends ExpressionBlock<Enchantment> {

    private static String[] enchants = {
            "ARROW_DAMAGE", "ARROW_FIRE", "ARROW_INFINITE", "ARROW_KNOCKBACK", "BINDING_CURSE", "CHANNELING",
            "DAMAGE_ALL", "DAMAGE_ARTHROPODS", "DAMAGE_UNDEAD", "DEPTH_STRIDER", "DIG_SPEED", "DURABILITY",
            "FIRE_ASPECT", "FROST_WALKER", "IMPALING", "KNOCKBACK", "LOOT_BONUS_BLOCKS", "LOOT_BONUS_MOBS",
            "LOYALTY", "LUCK", "LURE", "MENDING", "MULTISHOT", "OXYGEN", "PIERCING", "PROTECTION_ENVIRONMENTAL",
            "PROTECTION_EXPLOSIONS", "PROTECTION_FALL", "PROTECTION_FIRE", "PROTECTION_PROJECTILE", "QUICK_CHARGE",
            "RIPTIDE", "SILK_TOUCH", "SOUL_SPEED", "SWEEPING_EDGE", "THORNS", "VANISHING_CURSE", "WATER_WORKER"};

    @Override
    protected Syntax init() {
        getStyleClass().clear();
        return new Syntax(new ChoiceParameter(enchants));
    }

    @Override
    public String toJava() {
        return Enchantment.class.getCanonicalName() + "." + arg(0);
    }
}
