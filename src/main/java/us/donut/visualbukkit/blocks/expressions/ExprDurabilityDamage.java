package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerItemDamageEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;

@Description({"The durability damage in a PlayerItemDamageEvent", "Changers: set, add, remove", "Returns: number"})
@Event(PlayerItemDamageEvent.class)
public class ExprDurabilityDamage extends ChangeableExpressionBlock<Integer> {

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
    public String change(ChangeType changeType, String delta) {
        if (!PlayerItemDamageEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            throw new IllegalStateException();
        }
        switch (changeType) {
            case SET: return "event.setDamage(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
