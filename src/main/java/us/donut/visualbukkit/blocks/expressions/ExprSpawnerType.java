package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The entity type spawned by a spawner", "Returns: entity type"})
@Modifier(ModificationType.SET)
public class ExprSpawnerType extends ExpressionBlock<EntityType> {

    @Override
    protected Syntax init() {
        return new Syntax("spawner type of", CreatureSpawner.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSpawnedType()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setSpawnedType(" + delta + ");" : null;
    }
}
