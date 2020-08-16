package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The viewers of an inventory", "Returns: list of human entities"})
public class ExprInventoryViewers extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("viewers of", Inventory.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getViewers())";
    }
}
