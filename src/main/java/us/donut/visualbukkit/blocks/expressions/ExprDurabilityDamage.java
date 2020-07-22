package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerItemDamageEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;

@Description({"The durability damage in a PlayerItemDamageEvent", "Returns: number"})
@Event(PlayerItemDamageEvent.class)
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprDurabilityDamage extends ModifiableExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("durability damage");
    }

    @Override
    public String toJava() {
        if (PlayerItemDamageEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            return "event.getDamage()";
        }
        throw new IllegalStateException();
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        if (!PlayerItemDamageEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            throw new IllegalStateException();
        }
        switch (modificationType) {
            case SET: return "event.setDamage(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "-" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
