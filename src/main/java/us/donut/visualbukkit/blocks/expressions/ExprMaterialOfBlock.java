package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description({"The material of a block", "Returns: material"})
@Modifier(ModificationType.SET)
public class ExprMaterialOfBlock extends ModifiableExpressionBlock<Material> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("material of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setType(" + delta + ");" : null;
    }
}
