package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description({"The material of a block", "Returns: material"})
public class ExprMaterialOfBlock extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("material of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setType(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return Material.class;
    }
}
