package us.donut.visualbukkit.blocks.structures;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;

@Description("Defines a command")
public class StructCommand extends StructureBlock {

    private StringLiteralParameter nameParameter;

    @Override
    protected Syntax init() {
        nameParameter = new StringLiteralParameter();
        return new Syntax("command /", nameParameter, Syntax.LINE_SEPARATOR,
                "aliases:    ", new InputParameter(), Syntax.LINE_SEPARATOR,
                "description:", new InputParameter(), Syntax.LINE_SEPARATOR,
                "permission: ", new InputParameter());
    }

    @Override
    public void insertInto(JavaClassSource mainClass) {
        MethodSource<JavaClassSource> commandMethod = mainClass.getMethod("onCommand", "CommandSender", "Command", "String", "String[]");
        String childJava = getChildJava();
        commandMethod.setBody(
                "if (command.getName().equalsIgnoreCase(" + arg(0) + ")) {" +
                "try {" +
                BuildContext.declareLocalVariables() +
                childJava +
                "} catch (Exception e) { e.printStackTrace(); }}" +
                commandMethod.getBody());
    }

    public String getName() {
        return nameParameter.getText().trim();
    }

    public String getAliases() {
        return arg(1).trim();
    }

    public String getDescription() {
        return arg(2).trim();
    }

    public String getPermission() {
        return arg(3).trim();
    }
}
