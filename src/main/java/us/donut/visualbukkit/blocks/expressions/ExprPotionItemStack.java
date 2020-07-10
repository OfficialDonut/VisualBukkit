package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

import java.util.Arrays;

@Description({"A potion item stack", "Returns: item stack"})
public class ExprPotionItemStack extends ExpressionBlock<ItemStack> {

    private static String[] potionTypes = Arrays.stream(PotionType.values())
            .map(Enum::name)
            .sorted()
            .toArray(String[]::new);

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new ChoiceParameter(potionTypes), "potion item stack");
    }

    @Override
    public String toJava() {
        BuildContext.addUtilMethod("createPotion");
        return "UtilMethods.createPotion(" + PotionType.class.getCanonicalName() + "." + arg(0) + ")";
    }
}
