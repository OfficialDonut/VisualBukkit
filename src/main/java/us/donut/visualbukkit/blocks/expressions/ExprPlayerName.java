package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The name of a player", "Returns: string"})
public class ExprPlayerName extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("name of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }
}
