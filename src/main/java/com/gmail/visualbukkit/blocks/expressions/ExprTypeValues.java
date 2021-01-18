package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.List;

@Description("All values of a type")
@SuppressWarnings("rawtypes")
public class ExprTypeValues extends ExpressionBlock<List> {

    public ExprTypeValues() {
        init("all ", new ChoiceParameter("entity", "material", "particle", "sound"), " types");
    }

    @Override
    public String toJava() {
        Class<?> enumClass = null;
        switch (arg(0)) {
            case "entity": enumClass = EntityType.class; break;
            case "material": enumClass = Material.class; break;
            case "particle": enumClass = Particle.class; break;
            case "sound": enumClass = Sound.class; break;
        }
        return "PluginMain.createList(" + enumClass.getCanonicalName() + ".values())";
    }
}