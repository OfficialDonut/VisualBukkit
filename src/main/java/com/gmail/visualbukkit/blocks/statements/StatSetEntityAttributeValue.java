package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;

@Category(Category.ENTITY)
@Description("Sets the value of an entity attribute")
public class StatSetEntityAttributeValue extends StatementBlock {

    public StatSetEntityAttributeValue() {
        init("set entity attribute value");
        initLine("attribute: ", Attribute.class);
        initLine("entity:    ", Attributable.class);
        initLine("value:     ", double.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getAttribute(" + arg(0) + ").setBaseValue(" + arg(2) + ");";
    }
}
