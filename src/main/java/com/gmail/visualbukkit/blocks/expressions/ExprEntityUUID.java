package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.entity.Entity;

import java.util.UUID;

@Name("Entity UUID")
@Description("The UUID of an entity")
public class ExprEntityUUID extends ExpressionBlock<UUID> {

    public ExprEntityUUID() {
        init("UUID of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getUniqueId()";
    }
}