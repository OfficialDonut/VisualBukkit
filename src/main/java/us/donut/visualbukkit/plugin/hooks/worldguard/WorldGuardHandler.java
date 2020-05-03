package us.donut.visualbukkit.plugin.hooks.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.World;
import us.donut.visualbukkit.util.SimpleList;

public class WorldGuardHandler {

    private static RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();

    public static SimpleList getRegions(World world) {
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));
        return regionManager != null ? new SimpleList(regionManager.getRegions().values()) : new SimpleList();
    }

    public static SimpleList getRegions(Location location) {
        return new SimpleList(regionContainer.createQuery().getApplicableRegions(BukkitAdapter.adapt(location)).getRegions());
    }
}
