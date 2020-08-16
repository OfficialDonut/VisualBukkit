package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The sprinting state of a player", "Returns: boolean"})
@Modifier(ModificationType.SET)
public class ExprSprintingState extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("sprinting state of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isSprinting()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setSprinting(" + delta + ");" : null;
    }
}
