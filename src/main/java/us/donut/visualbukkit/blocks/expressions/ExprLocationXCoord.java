package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Location X-Coord")
@Category("Location")
@Description({"The x-coord of a location", "Returns: number"})
public class ExprLocationXCoord extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("x-coord of", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0)  + ".getX()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return arg(0) + ".setX(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }

    @Override
    public Class<?> getReturnType() {
        return double.class;
    }
}
