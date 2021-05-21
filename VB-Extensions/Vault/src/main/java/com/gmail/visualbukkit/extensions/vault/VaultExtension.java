package com.gmail.visualbukkit.extensions.vault;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class VaultExtension extends VisualBukkitExtension {

    public VaultExtension() throws IOException {
        PluginModule.register("Vault", VAULT_MODULE);
        BlockRegistry.register(this, "com.gmail.visualbukkit.extensions.vault", ResourceBundle.getBundle("CustomVaultBlocks"));
        try (InputStream inputStream = VaultExtension.class.getResourceAsStream("/GeneratedVaultBlocks.json")) {
            JSONArray jsonArray = new JSONArray(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            BlockRegistry.register(this, jsonArray, ResourceBundle.getBundle("GeneratedVaultBlocks"));
        }
    }

    @Override
    public String getName() {
        return "Vault";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Donut";
    }

    @Override
    public String getDescription() {
        return "Adds support for Vault";
    }

    protected final static PluginModule VAULT_MODULE = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.addMavenDependency(
                    "<groupId>com.github.MilkBowl</groupId>" +
                    "<artifactId>VaultAPI</artifactId>" +
                    "<version>1.7</version>" +
                    "<scope>provided</scope>");

            buildContext.getMainClass().addField("public static net.milkbowl.vault.chat.Chat vaultChat;");
            buildContext.getMainClass().addField("public static net.milkbowl.vault.economy.Economy vaultEconomy;");
            buildContext.getMainClass().addField("public static net.milkbowl.vault.permission.Permission vaultPermission;");

            buildContext.getMainClass().addMethod(
                    "public static net.milkbowl.vault.chat.Chat getVaultChat() {" +
                    "if (vaultChat == null) vaultChat = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class).getProvider();" +
                    "return vaultChat;" +
                    "}");

            buildContext.getMainClass().addMethod(
                    "public static net.milkbowl.vault.economy.Economy getVaultEconomy() {" +
                    "if (vaultEconomy == null) vaultEconomy = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();" +
                    "return vaultEconomy;" +
                    "}");

            buildContext.getMainClass().addMethod(
                    "public static net.milkbowl.vault.permission.Permission getVaultPermission() {" +
                    "if (vaultPermission == null) vaultPermission = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class).getProvider();" +
                    "return vaultPermission;" +
                    "}");
        }
    };
}
