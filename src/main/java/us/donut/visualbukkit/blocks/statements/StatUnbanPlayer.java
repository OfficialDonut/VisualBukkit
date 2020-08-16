package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Unbans a player name/IP")
@Category(StatementCategory.PLAYER)
public class StatUnbanPlayer extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("unban", new ChoiceParameter("name", "IP"), String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").pardon(" + arg(1) + ");";
    }
}
