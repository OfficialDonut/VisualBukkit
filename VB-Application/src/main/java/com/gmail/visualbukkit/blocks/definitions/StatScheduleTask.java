package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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
                Set<String> variables = new HashSet<>();
                Matcher matcher = ExprSimpleLocalVariable.VAR_PATTERN.matcher(childJava);

                while (matcher.find()) {
                    variables.add(matcher.group());
                }

                StringBuilder finalVarDeclarations = new StringBuilder();
                for (String variable : variables) {
                    String finalVar = variable.replace("$", "FINAL_" + UUID.randomUUID().toString().replace("-", ""));
                    finalVarDeclarations.append("Object ").append(finalVar).append(" = ").append(variable).append(";");
                    childJava = childJava.replace(variable, finalVar);
                }

                String runnable = "() -> {try {" + childJava + "} catch (Exception e) { e.printStackTrace(); }}";

                if (arg(1).equals("false")) {
                    return finalVarDeclarations + "Bukkit.getScheduler()." +
                            ((arg(0).equals("sync") ? "runTaskLater" : "runTaskLaterAsynchronously")) +
                            "(PluginMain.getInstance()," + runnable + "," + arg(2) + ");";
                } else {
                    return finalVarDeclarations + "Bukkit.getScheduler()." +
                            ((arg(0).equals("sync") ? "runTaskTimer" : "runTaskTimerAsynchronously")) +
                            "(PluginMain.getInstance()," + runnable + ",0," + arg(2) + ");";
                }
            }
        };
    }
}
