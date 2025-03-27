package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.project.BuildInfo;

import java.net.URI;

@BlockDefinition(id = "stat-cancel-scheduled-task", name = "Cancel Scheduled Task", description = "Cancels a scheduled task")
public class StatCancelScheduledTask extends StatementBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForContainer(StatScheduleTask.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/scheduler/BukkitScheduler.html#cancelTask(int)"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "cancel();";
    }
}
