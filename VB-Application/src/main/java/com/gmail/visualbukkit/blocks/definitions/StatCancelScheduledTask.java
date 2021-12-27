package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;

public class StatCancelScheduledTask extends Statement {

    public StatCancelScheduledTask() {
        super("stat-cancel-scheduled-task", "Cancel Scheduled Task", "Bukkit", "Cancels a scheduled task");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatScheduleTask.class);
            }

            @Override
            public String toJava() {
                return "cancel();";
            }
        };
    }
}
