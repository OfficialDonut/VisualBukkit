package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The item stacks dropped in an EntityDeathEvent", "Returns: list of item stacks"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprEventDeathDrops extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("death drops");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(event.getDrops())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case CLEAR: return "event.getDrops().clear();";
            case ADD: return "event.getDrops().add(" + delta + ");";
            case REMOVE: return "event.getDrops().remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return ItemStack.class;
    }
}
