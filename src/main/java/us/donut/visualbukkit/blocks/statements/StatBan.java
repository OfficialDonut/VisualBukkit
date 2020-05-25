package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Bans a player name/IP")
public class StatBan extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("ban", new ChoiceParameter("name", "IP"), String.class, "with reason", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").addBan(" + arg(1) + "," + arg(2) + ",null,null);";
    }
}
