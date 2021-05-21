package com.gmail.visualbukkit.extensions.vault;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprVaultChat extends Expression {

    public ExprVaultChat() {
        super("expr-vault-chat");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("net.milkbowl.vault.chat.Chat");
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
                return "PluginMain.getVaultChat()";
            }
        };
    }
}
