package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if an inventory contains a material", "Returns: boolean"})
public class ExprInventoryContainsMaterial extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Inventory.class, new ChoiceParameter("contains", "does not contain"), "at least", int.class, Material.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".contains(" + arg(3) + "," + arg(2) + ")";
    }
}
