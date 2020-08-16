package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Bans a player name/IP")
@Category(StatementCategory.PLAYER)
public class StatBanPlayer extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("ban player", Syntax.LINE_SEPARATOR,
                "ban type:", new ChoiceParameter("name", "IP"), Syntax.LINE_SEPARATOR,
                "name/IP: ", String.class, Syntax.LINE_SEPARATOR,
                "reason:  ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").addBan(" + arg(1) + "," + arg(2) + ",null,null);";
    }
}
