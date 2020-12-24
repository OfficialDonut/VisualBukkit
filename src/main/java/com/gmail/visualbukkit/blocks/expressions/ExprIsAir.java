package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;

@Description("Checks if a material is air")
public class ExprIsAir extends ExpressionBlock<Boolean> {

    public ExprIsAir() {
        init(Material.class, " is air");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isAir()";
    }
}