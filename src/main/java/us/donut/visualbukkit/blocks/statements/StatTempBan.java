package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Description("Temporarily bans a player name/IP")
public class StatTempBan extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("temp ban player")
                .line("ban type:", new ChoiceParameter("name", "IP"))
                .line("name/IP: ", String.class)
                .line("reason:  ", String.class)
                .line("duration:", Duration.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").addBan(" + arg(1) + "," + arg(2) + ",new Date(System.currentTimeMillis()+" + arg(3) + ".toMillis()),null);";
    }
}
