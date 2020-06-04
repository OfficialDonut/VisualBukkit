package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Module;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Category("Block")
@Description({"The data of a block", "Changers: set", "Returns: number"})
@Module(PluginModule.REFLECTION_UTIL)
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
        String blockVar = randomVar();
        return changeType == ChangeType.SET ?
                "Block " + blockVar + "=" + arg(0) + ";" +
                "ReflectionUtil" +
                        ".getDeclaredMethod(" + blockVar + ".getClass(),\"setData\",new Class[]{byte.class})" +
                        ".invoke(" + blockVar + ",new Object[]{new Byte(" + delta + ")});" : null;
    }
}
