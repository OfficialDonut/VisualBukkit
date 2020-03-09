package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description("Makes a player say a message")
public class StatForceChat extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("make", Player.class, "say", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".chat(" + arg(1) + ");";
    }
}
