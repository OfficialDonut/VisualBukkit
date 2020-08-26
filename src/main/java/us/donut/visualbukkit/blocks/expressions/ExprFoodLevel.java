package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The food level of a player", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprFoodLevel extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("food level of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFoodLevel()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return arg(0) + ".setFoodLevel(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
