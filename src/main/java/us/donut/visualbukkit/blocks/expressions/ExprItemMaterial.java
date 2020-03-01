package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.ArrayList;
import java.util.List;

@Description({"An item material", "Returns: material"})
public class ExprItemMaterial extends ExpressionBlock {

    private static String[] materials;

    static {
        List<String> materials = new ArrayList<>();
        for (Material value : Material.values()) {
            if (!value.isLegacy() && !value.isBlock()) {
                materials.add(value.name());
            }
        }
        ExprItemMaterial.materials = materials.toArray(new String[0]);
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(materials));
    }

    @Override
    public String toJava() {
        return getReturnType().getCanonicalName() + "." + arg(0);
    }

    @Override
    public Class<?> getReturnType() {
        return Material.class;
    }
}
