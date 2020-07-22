package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Category("Block")
@Description({"The data of a block", "Returns: number"})
@Modifier(ModificationType.SET)
public class ExprBlockData extends ModifiableExpressionBlock<Byte> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("data of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getData()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
        BuildContext.addUtilMethod("setBlockData");
        return modificationType == ModificationType.SET ? "UtilMethods.setBlockData(" + arg(0) + "," + delta + ");" : null;
    }
}
