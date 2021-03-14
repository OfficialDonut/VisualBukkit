package com.gmail.visualbukkit.extensions.vault;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.plugin.BuildContext;
import net.milkbowl.vault.economy.Economy;

public class ExprVaultEconomy extends Expression {

    public ExprVaultEconomy() {
        super("expr-vault-economy", Economy.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(VaultExtension.VAULT_MODULE);
            }

            @Override
            public String toJava() {
                return "PluginMain.getVaultEconomy()";
            }
        };
    }
}
