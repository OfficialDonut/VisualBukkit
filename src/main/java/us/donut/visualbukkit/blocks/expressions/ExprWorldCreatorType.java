package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The world type of a world creator", "Returns: WorldType"})
@Modifier(ModificationType.SET)
public class ExprWorldCreatorType extends ExpressionBlock<WorldType> {

    @Override
    protected Syntax init() {
        return new Syntax("world type of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".type()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".type(" + delta + ");" : null;
    }
}
