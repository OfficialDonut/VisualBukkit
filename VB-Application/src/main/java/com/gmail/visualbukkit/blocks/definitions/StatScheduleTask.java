package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

public class StatScheduleTask extends Container {

    public StatScheduleTask() {
        super("stat-schedule-task");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("sync", "async"), new ChoiceParameter("false", "true"), new ExpressionParameter(ClassInfo.LONG)) {
            @Override
            public String toJava() {
                String childJava = getChildJava();
                StringBuilder tempVarDeclarations = new StringBuilder();
                StringBuilder finalVarDeclarations = new StringBuilder();
                Set<String> variables = new HashSet<>();
                Matcher matcher = ExprSimpleLocalVariable.VAR_PATTERN.matcher(childJava);

                while (matcher.find()) {
                    String variable = matcher.group();
                    if (variables.add(variable)) {
                        String tempVar = ExprSimpleLocalVariable.getRandomVariable().replace("$", "TEMP_");
                        String finalVar = ExprSimpleLocalVariable.getRandomVariable().replace("$", "FINAL_");
                        tempVarDeclarations.append("Object ").append(tempVar).append(" = ").append(variable).append(";");
                        finalVarDeclarations.append("Object ").append(finalVar).append(" = ").append(tempVar).append(";");
                        childJava = childJava.replace(variable, finalVar);
                    }
                }

                String method;

                if (arg(1).equals("false")) {
                    method = ((arg(0).equals("sync") ? "runTaskLater" : "runTaskLaterAsynchronously")) +
                            "(PluginMain.getInstance()," + arg(2) + ");";
                } else {
                    method = ((arg(0).equals("sync") ? "runTaskTimer" : "runTaskTimerAsynchronously")) +
                            "(PluginMain.getInstance(),0," + arg(2) + ");";
                }

                return tempVarDeclarations +
                        "new org.bukkit.scheduler.BukkitRunnable() {" +
                        "public void run() {" +
                        "try {" +
                        finalVarDeclarations +
                        childJava +
                        "} catch (Exception ex) { ex.printStackTrace(); }}}." + method;
            }
        };
    }
}
