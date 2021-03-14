package com.gmail.visualbukkit.extensions.vault;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.plugin.BuildContext;
import net.milkbowl.vault.permission.Permission;

public class ExprVaultPermission extends Expression {

    public ExprVaultPermission() {
        super("expr-vault-permission", Permission.class);
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
                return "PluginMain.getVaultPermission()";
            }
        };
    }
}
