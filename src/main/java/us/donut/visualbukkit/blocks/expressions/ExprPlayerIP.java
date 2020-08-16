package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("Player IP")
@Description({"The IP of a player", "Returns: string"})
public class ExprPlayerIP extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("IP of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getAddress().getHostName()";
    }
}
