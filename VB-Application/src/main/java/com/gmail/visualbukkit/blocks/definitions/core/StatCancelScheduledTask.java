package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.project.BuildInfo;

@BlockDefinition(id = "stat-cancel-scheduled-task", name = "Cancel Scheduled Task", description = "Cancels a scheduled task")
public class StatCancelScheduledTask extends StatementBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForContainer(StatScheduleTask.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "cancel();";
    }
}
