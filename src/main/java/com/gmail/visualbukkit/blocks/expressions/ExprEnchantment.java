package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.enchantments.Enchantment;

@Description("An enchantment")
public class ExprEnchantment extends ExpressionBlock<Enchantment> {

    private static String[] enchants = {
            "ARROW_DAMAGE", "ARROW_FIRE", "ARROW_INFINITE", "ARROW_KNOCKBACK", "BINDING_CURSE", "CHANNELING",
            "DAMAGE_ALL", "DAMAGE_ARTHROPODS", "DAMAGE_UNDEAD", "DEPTH_STRIDER", "DIG_SPEED", "DURABILITY",
            "FIRE_ASPECT", "FROST_WALKER", "IMPALING", "KNOCKBACK", "LOOT_BONUS_BLOCKS", "LOOT_BONUS_MOBS",
            "LOYALTY", "LUCK", "LURE", "MENDING", "MULTISHOT", "OXYGEN", "PIERCING", "PROTECTION_ENVIRONMENTAL",
            "PROTECTION_EXPLOSIONS", "PROTECTION_FALL", "PROTECTION_FIRE", "PROTECTION_PROJECTILE", "QUICK_CHARGE",
            "RIPTIDE", "SILK_TOUCH", "SOUL_SPEED", "SWEEPING_EDGE", "THORNS", "VANISHING_CURSE", "WATER_WORKER"};

    public ExprEnchantment() {
        init(new ChoiceParameter(enchants));
    }

    @Override
    public String toJava() {
        return Enchantment.class.getCanonicalName() + "." + arg(0);
    }
}