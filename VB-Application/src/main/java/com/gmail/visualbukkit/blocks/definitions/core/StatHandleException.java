package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.project.BuildInfo;

@BlockDefinition(id = "stat-handle-exception", name = "Handle Exception", description = "Checks if an exception occurred during the previous 'Attempt Execution'")
public class StatHandleException extends ContainerBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForPrevious(StatAttemptExecution.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "catch (Exception $executionException" + getNestedContainers(StatHandleException.class) + ") {" + generateChildrenJava(buildInfo) + "}";
    }
}
