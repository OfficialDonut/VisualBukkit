package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The clicked entity in an PlayerInteractEntityEvent", "Returns: entity"})
@Event(PlayerInteractEvent.class)
public class ExprClickedEntity extends ExpressionBlock<Entity> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clicked entity");
    }

    @Override
    public String toJava() {
        return "event.getRightClicked()";
    }
}
