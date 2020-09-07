package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Description("Schedules a task to run")
public class StatScheduleTask extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax(new BinaryChoiceParameter("sync", "async"), "execute", new BinaryChoiceParameter("after", "every"), Duration.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        StatementBlock block = this;
        while (block != null) {
            if (block instanceof ParentBlock.ChildConnector) {
                block = ((ParentBlock.ChildConnector) block).getParentStatement();
                if (block instanceof StatScheduleTask) {
                    throw new IllegalStateException();
                }
            } else {
                block = block.getPrevious();
            }
        }
    }

    @Override
    public String toJava() {
        Set<String> parentVariables = new HashSet<>(BuildContext.getLocalVariables());
        BuildContext.getLocalVariables().clear();
        String childJava = getChildJava();
        Set<String> childVariables = new HashSet<>(BuildContext.getLocalVariables());
        StringBuilder finalDeclarations = new StringBuilder();
        StringBuilder innerDeclarations = new StringBuilder();
        for (String variable : childVariables) {
            String innerVariable = randomVariable();
            childJava = childJava.replace(variable, innerVariable);
            if (parentVariables.contains(variable)) {
                String finalVariable = randomVariable();
                finalDeclarations.append("Object ").append(finalVariable).append("=").append(variable).append(";");
                innerDeclarations.append("Object ").append(innerVariable).append("=").append(finalVariable).append(";");
            } else {
                innerDeclarations.append("Object ").append(innerVariable).append("=").append("null").append(";");
            }
        }
        BuildContext.getLocalVariables().clear();
        BuildContext.getLocalVariables().addAll(parentVariables);
        String method;
        if (arg(1).equals("after")) {
            method = (arg(0).equals("synchronously") ? ".runTaskLater" : ".runTaskLaterAsynchronously") + "(PluginMain.getInstance()," + arg(2) + ".getSeconds() * 20);";
        } else {
            method = (arg(0).equals("synchronously") ? ".runTaskTimer" : ".runTaskTimerAsynchronously") + "(PluginMain.getInstance(), 0, " + arg(2) + ".getSeconds() * 20);";
        }
        return finalDeclarations +
                "new org.bukkit.scheduler.BukkitRunnable() {" +
                innerDeclarations +
                "public void run() {" +
                "try {" +
                childJava +
                "} catch (Exception ex) { ex.printStackTrace(); }}}" + method;
    }

    private static String randomVariable() {
        return "a" + UUID.randomUUID().toString().replace("-", "");
    }
}
