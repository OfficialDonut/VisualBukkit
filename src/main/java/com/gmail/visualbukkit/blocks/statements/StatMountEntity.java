package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Makes an entity ride another")
public class StatMountEntity extends StatementBlock {

    public StatMountEntity() {
        init("mount ", Entity.class, " to ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addPassenger(" + arg(0) + ");";
    }
}
