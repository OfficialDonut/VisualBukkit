package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Module;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

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
        return changeType == ChangeType.SET ? "setBlockData(" + arg(0) + "," + delta + ");" : null;
    }

    @UtilMethod
    public static void setBlockData(Block block, byte data) throws InvocationTargetException, IllegalAccessException {
        ReflectionUtil.getDeclaredMethod(block.getClass(), "setData", byte.class).invoke(block, data);
    }
}
