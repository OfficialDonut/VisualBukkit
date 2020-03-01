package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description("Hides a player from another player")
public class StatHidePlayer extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hide", Player.class, "from", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".hidePlayer(this," + arg(0) + ");";
    }
}
