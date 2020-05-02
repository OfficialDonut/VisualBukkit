package us.donut.visualbukkit.plugin.hooks;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

public class VaultHook implements PluginHook {

    public VaultHook() {
        ClassPool.getDefault().importPackage("net.milkbowl.vault.economy");
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtField field = CtField.make("private Economy vaultEconomy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();", mainClass);
        mainClass.addField(field);
    }
}
