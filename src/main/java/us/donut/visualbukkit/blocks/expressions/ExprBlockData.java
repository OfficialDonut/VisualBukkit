package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Category("Block")
@Description({"The data of a block", "Changers: set", "Returns: number"})
public class ExprBlockData extends ChangeableExpressionBlock<Byte> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("data of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getData()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        BuildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
        BuildContext.addUtilMethod("setBlockData");
        return changeType == ChangeType.SET ? "UtilMethods.setBlockData(" + arg(0) + "," + delta + ");" : null;
    }
}
