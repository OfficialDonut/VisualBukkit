package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import org.apache.commons.text.StringEscapeUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.regex.Pattern;

@BlockDefinition(uid = "comp-command", name = "Command")
public class CompCommand extends PluginComponentBlock {

    private static final Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\S*");

    private final InputParameter name = new InputParameter();
    private final InputParameter aliases = new InputParameter();
    private final InputParameter descr = new InputParameter();
    private final InputParameter perm = new InputParameter();
    private final InputParameter permMessage = new InputParameter();
    private final InputParameter usage = new InputParameter();

    public CompCommand() {
        name.textProperty().addListener(((o, oldValue, newValue) -> {
            if (!WHITE_SPACE_PATTERN.matcher(newValue).matches()) {
                name.setText(oldValue);
            }
        }));
        addParameter("Name", name);
        addParameter("Alias", aliases);
        addParameter("Description", descr);
        addParameter("Permission", perm);
        addParameter("Permission Message", permMessage);
        addParameter("Usage", usage);
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        MethodSource<JavaClassSource> commandMethod = buildInfo.getMainClass().getMethod("onCommand", "CommandSender", "Command", "String", "String[]");
        commandMethod.setBody(
                "if (command.getName().equalsIgnoreCase(\"" + StringEscapeUtils.escapeJava(arg(0)) + "\")) {" +
                "try {" +
                buildInfo.getLocalVariableDeclarations() +
                generateChildrenJava() +
                "} catch (Exception e) { e.printStackTrace(); }" +
                "return true;" +
                "}" +
                commandMethod.getBody());
    }

    public String getName() {
        return name.getText();
    }

    public String getAliases() {
        return aliases.getText();
    }

    public String getDescription() {
        return descr.getText();
    }

    public String getPermission() {
        return perm.getText();
    }

    public String getPermissionMessage() {
        return permMessage.getText();
    }

    public String getUsage() {
        return usage.getText();
    }
}
