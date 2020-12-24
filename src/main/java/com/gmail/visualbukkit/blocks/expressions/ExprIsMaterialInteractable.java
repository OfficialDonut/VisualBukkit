package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;

@Description("Checks if a material is interactable")
public class ExprIsMaterialInteractable extends ExpressionBlock<Boolean> {

    public ExprIsMaterialInteractable() {
        init(Material.class, " is interactable");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isInteractable()";
    }
}