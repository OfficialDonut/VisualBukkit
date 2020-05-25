package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Unbans a player name/IP")
public class StatUnban extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("unban", new ChoiceParameter("name", "IP"), String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").pardon(" + arg(1) + ");";
    }
}
