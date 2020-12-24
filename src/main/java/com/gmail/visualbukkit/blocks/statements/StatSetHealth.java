package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Damageable;

@Category(Category.ENTITY)
@Description("Sets the health of a living entity")
public class StatSetHealth extends StatementBlock {

    public StatSetHealth() {
        init("set health of ", Damageable.class, " to ", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setHealth(" + arg(1) + ");";
    }
}
