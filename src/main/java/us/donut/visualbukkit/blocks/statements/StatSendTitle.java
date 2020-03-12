package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Category("Player")
@Description("Sends a title to a player")
public class StatSendTitle extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(
                "send title", String.class, "with subtitle", String.class, "to", Player.class,
                "and fade in for", Duration.class, "stay for", Duration.class, "fade out for", Duration.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".sendTitle(" + arg(0) + "," + arg(1) + ",(int)" +
                arg(3) + ".getSeconds()*20,(int)" + arg(4) + ".getSeconds()*20,(int)" + arg(5) + ".getSeconds()*20);";
    }
}
