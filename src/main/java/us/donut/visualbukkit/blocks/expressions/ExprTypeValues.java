package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"All values of a type", "Returns: list of types"})
public class ExprTypeValues extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("all", new ChoiceParameter("entity", "material", "particle", "sound"), "types");
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
        return "new SimpleList(" + enumClass.getCanonicalName() + ".values())";
    }
}
