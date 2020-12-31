package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;

@Description("Checks if a material is air")
public class ExprIsMaterialAir extends ExpressionBlock<Boolean> {

    public ExprIsMaterialAir() {
        init(Material.class, " is air");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isAir()";
    }
}