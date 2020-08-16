package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.command.CommandSender;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The executor of the command", "Returns: command sender"})
public class ExprCommandSender extends ExpressionBlock<CommandSender> {

    @Override
    protected Syntax init() {
        return new Syntax("command sender");
    }

    @Override
    public String toJava() {
        return "sender";
    }
}
