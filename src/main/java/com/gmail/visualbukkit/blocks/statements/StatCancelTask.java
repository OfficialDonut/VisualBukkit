package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Cancels a scheduled task")
public class StatCancelTask extends StatementBlock {

    public StatCancelTask() {
        init("cancel task");
    }

    @Override
    public void update() {
        super.update();
        validateParent("Cancel task must be used in a scheduled task", StatScheduleTask.class);
    }

    @Override
    public String toJava() {
        return "cancel();";
    }
}
