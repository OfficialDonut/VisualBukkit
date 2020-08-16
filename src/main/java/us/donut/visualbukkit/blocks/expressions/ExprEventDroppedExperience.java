package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDeathEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The amount of experience dropped in an EntityDeathEvent", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprEventDroppedExperience extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("dropped experience");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDroppedExp()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return "event.setDroppedExp(" + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
