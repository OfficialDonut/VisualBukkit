package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Description("Schedules a task to run")
public class StatScheduleTask extends ParentBlock {

    private BinaryChoiceParameter syncChoice = new BinaryChoiceParameter("sync", "async");
    private BinaryChoiceParameter modeChoice = new BinaryChoiceParameter("after", "every");

    public StatScheduleTask() {
        init(syncChoice, " execute ", modeChoice, " ", long.class, " ticks");
    }

    @Override
    public String toJava() {
        String method;
        if (modeChoice.isFirst()) {
            method = (syncChoice.isFirst() ? ".runTaskLater" : ".runTaskLaterAsynchronously") + "(PluginMain.getInstance()," + arg(2) + ");";
        } else {
            method = (syncChoice.isFirst() ? ".runTaskTimer" : ".runTaskTimerAsynchronously") + "(PluginMain.getInstance(), 0, " + arg(2) + ");";
        }
        return "new org.bukkit.scheduler.BukkitRunnable() {" +
                "public void run() {" +
                "try {" +
                getChildJava() +
                "} catch (Exception ex) { ex.printStackTrace(); }}}" + method;
    }
}
