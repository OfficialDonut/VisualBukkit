package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"An entity type", "Returns: entity type"})
public class ExprEntityType extends ExpressionBlock {

    private static String[] entityTypes = new String[EntityType.values().length];

    static {
        for (int i = 0; i < EntityType.values().length; i++) {
            entityTypes[i] = EntityType.values()[i].name();
        }
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(entityTypes));
    }

    @Override
    public String toJava() {
        return EntityType.class.getCanonicalName() + "." + arg(0);
    }

    @Override
    public Class<?> getReturnType() {
        return EntityType.class;
    }
}
