package us.donut.visualbukkit.plugin;

import org.bukkit.scheduler.BukkitRunnable;

public class ProcedureRunnable extends BukkitRunnable {

    private String procedureName;
    private Object[] args;

    public ProcedureRunnable(String procedureName, Object... args) {
        this.procedureName = procedureName;
        this.args = args;
    }

    @Override
    public void run() {
        PluginMain.procedure(procedureName, args);
    }
}
