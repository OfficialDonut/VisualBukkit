package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Removes a potion effect from a living entity")
public class StatRemovePotionEffect extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("remove", PotionEffectType.class, "from", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".removePotionEffect(" + arg(0) + ");";
    }
}
