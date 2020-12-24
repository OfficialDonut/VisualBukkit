package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Sittable;

@Category(Category.ENTITY)
@Description("Sets the sitting state of an entity that can sit")
public class StatSetSittingState extends StatementBlock {

    public StatSetSittingState() {
        init("set sitting state of ", Sittable.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSitting(" + arg(1) + ");";
    }
}
