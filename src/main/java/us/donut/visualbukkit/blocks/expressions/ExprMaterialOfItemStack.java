package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Item Stack")
@Description({"The material of an item stack", "Changers: set", "Returns: material"})
public class ExprMaterialOfItemStack extends ChangeableExpressionBlock<Material> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("material of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setType(" + delta + ");" : null;
    }
}
