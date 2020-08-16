package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerItemDamageEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The durability damage in a PlayerItemDamageEvent", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprEventDurabilityDamage extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("durability damage");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerItemDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDamage()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return "event.setDamage(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
