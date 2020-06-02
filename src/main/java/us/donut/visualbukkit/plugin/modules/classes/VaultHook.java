package us.donut.visualbukkit.plugin.modules.classes;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

public class VaultHook {

    private static Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();

    public static Economy getEconomy() {
        return economy;
    }
}
