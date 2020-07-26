package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Category("Entity")
@Description({"The passengers of an entity", "Returns: list of entities"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE})
public class ExprPassengers extends ModifiableExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("passengers of", Entity.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getPassengers())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case ADD: return arg(0) + ".addPassenger(" + delta + ");";
            case REMOVE: return arg(0) + ".removePassenger(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return Entity.class;
    }
}
