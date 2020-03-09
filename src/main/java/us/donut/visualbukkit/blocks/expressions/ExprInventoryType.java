package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"An inventory type", "Returns: inventory type"})
public class ExprInventoryType extends ExpressionBlock {

    private static String[] inventoryTypes = new String[EntityType.values().length];

    static {
        for (int i = 0; i < InventoryType.values().length; i++) {
            inventoryTypes[i] = InventoryType.values()[i].name();
        }
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(inventoryTypes));
    }

    @Override
    public String toJava() {
        return InventoryType.class.getCanonicalName() + "." + arg(0);
    }

    @Override
    public Class<?> getReturnType() {
        return InventoryType.class;
    }
}
