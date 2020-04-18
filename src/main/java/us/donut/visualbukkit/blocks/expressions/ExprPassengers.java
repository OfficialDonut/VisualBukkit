package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("Entity")
@Description({"The passengers of an entity", "Changers: add, remove", "Returns: list of entities"})
public class ExprPassengers extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("passengers of", Entity.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getPassengers())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case ADD: return arg(0) + ".addPassenger(" + delta + ");";
            case REMOVE: return arg(0) + ".removePassenger(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return Entity.class;
    }
}
