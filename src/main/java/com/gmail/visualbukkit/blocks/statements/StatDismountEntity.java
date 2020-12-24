package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Category(Category.ENTITY)
@Description("Makes an entity stop riding another")
public class StatDismountEntity extends StatementBlock {

    public StatDismountEntity() {
        init("dismount ", Entity.class, " from ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".removePassenger(" + arg(0) + ");";
    }
}
