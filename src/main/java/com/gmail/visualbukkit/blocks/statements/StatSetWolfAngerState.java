package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Wolf;

@Category(Category.ENTITY)
@Description("Sets the anger state of a wolf")
public class StatSetWolfAngerState extends StatementBlock {

    public StatSetWolfAngerState() {
        init("set anger state of ", Wolf.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setAngry(" + arg(1) + ");";
    }
}
