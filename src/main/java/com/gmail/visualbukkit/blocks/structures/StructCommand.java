package com.gmail.visualbukkit.blocks.structures;

import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.components.InputParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

@Description("Defines a command")
public class StructCommand extends StructureBlock {

    private StringLiteralParameter name = new StringLiteralParameter();
    private InputParameter aliases = new InputParameter();
    private InputParameter description = new InputParameter();
    private InputParameter permission = new InputParameter();
    private InputParameter permissionMessage = new InputParameter();

    public StructCommand() {
        init("command /", name);
        initLine("aliases:     ", aliases);
        initLine("description: ", description);
        initLine("permission:  ", permission);
        initLine("perm-message:", permissionMessage);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> commandMethod = context.getMainClass().getMethod("onCommand", "CommandSender", "Command", "String", "String[]");
        commandMethod.setBody(
                "if (command.getName().equalsIgnoreCase(" + name.toJava() + ")) {" +
                "try {" +
                getChildJava() +
                "} catch (Exception e) { e.printStackTrace(); }}" +
                commandMethod.getBody());
    }

    public String getName() {
        return name.getText().trim();
    }

    public String getAliases() {
        return aliases.getText().trim();
    }

    public String getDescription() {
        return description.getText().trim();
    }

    public String getPermission() {
        return permission.getText().trim();
    }

    public String getPermissionMessage() {
        return permissionMessage.getText().trim();
    }
}
