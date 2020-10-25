package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description({"The data of a block", "Returns: number"})
@Modifier(ModificationType.SET)
public class ExprBlockData extends ExpressionBlock<Byte> {

    @Override
    protected Syntax init() {
        return new Syntax("data of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getData()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addPluginModule(PluginModule.REFLECTION_UTIL);
        return modificationType == ModificationType.SET ?
                "ReflectionUtil" +
                        ".getDeclaredMethod(" + arg(0) + ".getClass(), \"setData\", byte.class)" +
                        ".invoke(" + arg(0) + "," + delta + ");" : null;
    }
}
