package com.gmail.visualbukkit.extensions.vault;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.plugin.BuildContext;

public class ExprVaultEconomy extends Expression {

    public ExprVaultEconomy() {
        super("expr-vault-economy", ClassInfo.of("net.milkbowl.vault.economy.Economy"));
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
