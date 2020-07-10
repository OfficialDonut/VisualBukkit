package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description({"The balance of a player", "Changers: add, remove", "Returns: number", "Requires: Vault"})
public class ExprPlayerBalance extends ChangeableExpressionBlock<Double> {

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
    public String change(ChangeType changeType, String delta) {
        BuildContext.addPluginModule(PluginModule.VAULT);
        switch (changeType) {
            case ADD: return "VaultHook.getEconomy().depositPlayer(" + arg(0) + "," + delta + ");";
            case REMOVE: return "VaultHook.getEconomy().withdrawPlayer(" + arg(0) + "," + delta + ");";
            default: return null;
        }
    }
}
