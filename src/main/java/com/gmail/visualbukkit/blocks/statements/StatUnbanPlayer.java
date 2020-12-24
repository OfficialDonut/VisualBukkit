package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;

@Category(Category.PLAYER)
@Description("Unbans a player name/IP")
public class StatUnbanPlayer extends StatementBlock {

    public StatUnbanPlayer() {
        init("unban ", new ChoiceParameter("name", "IP"), " ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getBanList(BanList.Type." + arg(0).toUpperCase() + ").pardon(" + arg(1) + ");";
    }
}
