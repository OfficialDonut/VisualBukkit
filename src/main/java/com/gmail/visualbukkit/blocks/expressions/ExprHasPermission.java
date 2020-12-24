package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.permissions.Permissible;

@Description("Checks if a player/console has a permission")
public class ExprHasPermission extends ExpressionBlock<Boolean> {

    public ExprHasPermission() {
        init(Permissible.class, " has permission ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hasPermission(" + arg(1) + ")";
    }
}