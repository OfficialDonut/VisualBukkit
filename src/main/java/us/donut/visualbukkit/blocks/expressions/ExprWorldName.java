package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The name of a world", "Returns: string"})
public class ExprWorldName extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("name of", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }
}
