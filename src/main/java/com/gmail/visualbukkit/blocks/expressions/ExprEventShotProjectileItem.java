package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

@Description("The projectile item shot in an EntityShootBowEvent")
public class ExprEventShotProjectileItem extends ExpressionBlock<ItemStack> {

    public ExprEventShotProjectileItem() {
        init("shot projectile item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Shot projectile item must be used in an EntityShootBowEvent", EntityShootBowEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getConsumable()";
    }
}
