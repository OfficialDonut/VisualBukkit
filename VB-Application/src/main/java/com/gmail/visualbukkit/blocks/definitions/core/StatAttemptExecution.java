package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import org.apache.commons.lang3.RandomStringUtils;

@BlockDefinition(id = "stat-attempt-execution", name = "Attempt Execution", description = "Attempts to execute code and suppresses any exceptions")
public class StatAttemptExecution extends ContainerBlock {

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "try {" + generateChildrenJava(buildInfo) + (getParentStatementHolder().getNext(this) instanceof StatHandleException ? "}" : ("} catch (Exception $" + RandomStringUtils.randomAlphanumeric(16) + ") {}"));
    }
}
