package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;

@Category(Category.PLAYER)
@Description("Bans a player name/IP")
public class StatBanPlayer extends StatementBlock {

    public StatBanPlayer() {
        init("ban player");
        initLine("ban type: ", new ChoiceParameter("name", "IP"));
        initLine("name/IP:  ", String.class);
        initLine("reason:   ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").addBan(" + arg(1) + "," + arg(2) + ",null,null);";
    }
}
