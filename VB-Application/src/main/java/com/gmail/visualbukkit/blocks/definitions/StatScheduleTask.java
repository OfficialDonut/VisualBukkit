package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatScheduleTask extends Container {

    private static final Pattern VAR_PATTERN = Pattern.compile("\\$[a-z0-9]{32}");

    public StatScheduleTask() {
        super("stat-schedule-task");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("sync", "async"), new ChoiceParameter("false", "true"), new ExpressionParameter(long.class)) {

            private String id = UUID.randomUUID().toString().replace("-", "");
            private Set<String> variables;

            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);

                variables = new HashSet<>();
                String childJava = getChildJava();

                Matcher matcher = VAR_PATTERN.matcher(childJava);
                while (matcher.find()) {
                    variables.add(matcher.group());
                }

                String varString = String.join(", Object ", variables);
                if (!varString.isEmpty()) {
                    varString = "Object " + varString;
                }

                buildContext.getUtilMethods().add(
                        "public static Runnable getTask_" + id + "(" + varString + ") {" +
                        "return () -> {" +
                        "try {" +
                        childJava +
                        "} catch (Exception e) { e.printStackTrace(); }" +
                        "};}");
            }

            @Override
            public String toJava() {
                String runnable = "PluginMain.getTask_" + id + "(" + String.join(",", variables) + ")";
                if (arg(1).equals("false")) {
                    return "Bukkit.getScheduler()." +
                            ((arg(0).equals("sync") ? "runTaskLater" : "runTaskLaterAsynchronously")) +
                            "(PluginMain.getInstance()," + runnable + "," + arg(2) + ");";
                } else {
                    return "Bukkit.getScheduler()." +
                            ((arg(0).equals("sync") ? "runTaskTimer" : "runTaskTimerAsynchronously")) +
                            "(PluginMain.getInstance()," + runnable + ",0," + arg(2) + ");";
                }
            }
        };
    }
}
