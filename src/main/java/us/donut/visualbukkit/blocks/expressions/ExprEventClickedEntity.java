package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The clicked entity in an PlayerInteractEntityEvent", "Returns: entity"})
public class ExprEventClickedEntity extends ExpressionBlock<Entity> {

    @Override
    protected Syntax init() {
        return new Syntax("clicked entity");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerInteractEntityEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getRightClicked()";
    }
}
