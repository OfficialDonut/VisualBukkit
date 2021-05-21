package com.gmail.visualbukkit.extensions.vault;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprVaultPermission extends Expression {

    public ExprVaultPermission() {
        super("expr-vault-permission");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("net.milkbowl.vault.permission.Permission");
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
