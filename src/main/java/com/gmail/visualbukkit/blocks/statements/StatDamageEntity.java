package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Damageable;

@Category(Category.ENTITY)
@Description("Damages an entity")
public class StatDamageEntity extends StatementBlock {

    public StatDamageEntity() {
        init("damage ", Damageable.class, " by ", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".damage(" + arg(1) + ");";
    }
}
