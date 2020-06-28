package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

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
        return "createPotion(" + PotionType.class.getCanonicalName() + "." + arg(0) + ")";
    }

    @UtilMethod
    public static ItemStack createPotion(PotionType potionType) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        if (potionMeta != null) {
            potionMeta.setBasePotionData(new PotionData(potionType));
            item.setItemMeta(potionMeta);
        }
        return item;
    }
}
