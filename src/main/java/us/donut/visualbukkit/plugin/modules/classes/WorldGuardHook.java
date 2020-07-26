package us.donut.visualbukkit.plugin.modules.classes;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.World;
import us.donut.visualbukkit.plugin.PluginMain;

import java.util.ArrayList;
import java.util.List;

public class WorldGuardHook {

    private static RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();

    public static List<Object> getRegions(World world) {
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));
        return regionManager != null ? PluginMain.createList(regionManager.getRegions().values()) : new ArrayList<>();
    }

    public static List<Object> getRegions(Location location) {
        return PluginMain.createList(regionContainer.createQuery().getApplicableRegions(BukkitAdapter.adapt(location)).getRegions());
    }
}
