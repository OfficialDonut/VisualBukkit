package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.Color;

@Name("RGB Color")
@Description("An RGB color")
public class ExprRGBColor extends ExpressionBlock<Color> {

    public ExprRGBColor() {
        init("rgb(", int.class, ",", int.class, ",", int.class, ")");
    }


    @Override
    public String toJava() {
        return "Color.fromRGB(" + arg(0) + "," + arg(1) + "," + arg(2) + ")";
    }
}
