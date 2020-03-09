package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description("Makes a player run a command")
public class StatForceCommand extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("make", Player.class, "run", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".performCommand(" + arg(1) + ");";
    }
}
