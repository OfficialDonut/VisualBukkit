package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description({"The balance of a player", "Returns: number", "Requires: Vault"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE})
public class ExprPlayerBalance extends ModifiableExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("balance of", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.VAULT);
        return "VaultHook.getEconomy().getBalance(" + arg(0) + ")";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addPluginModule(PluginModule.VAULT);
        switch (modificationType) {
            case ADD: return "VaultHook.getEconomy().depositPlayer(" + arg(0) + "," + delta + ");";
            case REMOVE: return "VaultHook.getEconomy().withdrawPlayer(" + arg(0) + "," + delta + ");";
            default: return null;
        }
    }
}
