package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.blocks.parameters.CheckBoxParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.reflection.ClassInfo;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

@BlockDefinition(uid = "stat-schedule-task", name = "Schedule Task")
public class StatScheduleTask extends ContainerBlock {

    private final CheckBoxParameter typeParameter = new CheckBoxParameter("Asynchronous");
    private final CheckBoxParameter modeParameter = new CheckBoxParameter("Repeated");

    public StatScheduleTask() {
        addParameter("Type", typeParameter);
        addParameter("Mode", modeParameter);
        addParameter("Delay", new ExpressionParameter(ClassInfo.of(long.class)));
    }

    @Override
    public String generateJava() {
        String childrenJava = generateChildrenJava();
        Set<String> localVars = new HashSet<>();
        StringBuilder tempVars = new StringBuilder();
        StringBuilder finalVars = new StringBuilder();
        Matcher matcher = ExprLocalVariable.VAR_PATTERN.matcher(childrenJava);
        while (matcher.find()) {
            String variable = matcher.group();
            if (localVars.add(variable)) {
                String tempVar = "$TEMP_" + RandomStringUtils.randomAlphabetic(16);
                String finalVar = "$FINAL_" + RandomStringUtils.randomAlphabetic(16);
                tempVars.append("Object ").append(tempVar).append(" = ").append(variable).append(";");
                finalVars.append("Object ").append(finalVar).append(" = ").append(tempVar).append(";");
                childrenJava = childrenJava.replace(variable, finalVar);
            }
        }

        String method;
        if (modeParameter.isSelected()) {
            method = (typeParameter.isSelected() ? "runTaskTimerAsynchronously" : "runTaskTimer") + "(PluginMain.getInstance(),0," + arg(2) + ");";
        } else {
            method = (typeParameter.isSelected() ? "runTaskLaterAsynchronously" : "runTaskLater") + "(PluginMain.getInstance()," + arg(2) + ");";
        }

        return tempVars +
                "new org.bukkit.scheduler.BukkitRunnable() {" +
                finalVars +
                "public void run() { try {"
                + childrenJava +
                "} catch (Exception ex) { ex.printStackTrace(); }}}." + method;
    }
}
