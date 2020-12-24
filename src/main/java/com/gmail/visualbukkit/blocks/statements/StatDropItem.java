package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Category(Category.WORLD)
@Description("Drops an item at a location")
public class StatDropItem extends StatementBlock {

    public StatDropItem() {
        init("drop item");
        initLine("item:     ", ItemStack.class);
        initLine("location: ", Location.class);
        initLine("type:     ", new ChoiceParameter("natural", "unnatural"));
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().drop" + (arg(2).equals("natural") ? "ItemNaturally" : "Item") + "(" + arg(1) + "," + arg(0) + ");";
    }
}
