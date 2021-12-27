package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;
import javafx.beans.binding.Bindings;
import org.apache.commons.text.StringEscapeUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.regex.Pattern;

public class CompCommand extends PluginComponent {

    private static final Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\S*");

    public CompCommand() {
        super("comp-command", "Command", "Bukkit", "Defines a command");
    }

    @Override
    public Block createBlock() {
        InputParameter nameParameter = new InputParameter("Name");
        nameParameter.getControl().textProperty().addListener(((o, oldValue, newValue) -> {
            if (!WHITE_SPACE_PATTERN.matcher(newValue).matches()) {
                nameParameter.getControl().setText(oldValue);
            }
        }));

        return new Block(nameParameter, new InputParameter("Aliases"), new InputParameter("Description"), new InputParameter("Permission"), new InputParameter("Permission Message"), new InputParameter("Usage")) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                MethodSource<JavaClassSource> commandMethod = buildContext.getMainClass().getMethod("onCommand", "CommandSender", "Command", "String", "String[]");
                commandMethod.setBody(
                        "if (command.getName().equalsIgnoreCase(\"" + StringEscapeUtils.escapeJava(arg(0)) + "\")) {" +
                        buildContext.getLocalVariableDeclarations() +
                        "try {" +
                        getChildJava() +
                        "} catch (Exception e) { e.printStackTrace(); }" +
                        "return true;" +
                        "}" +
                        commandMethod.getBody());
            }
        };
    }

    public class Block extends PluginComponent.Block {

        private InputParameter name;
        private InputParameter aliases;
        private InputParameter descr;
        private InputParameter perm;
        private InputParameter permMessage;
        private InputParameter usage;

        public Block(InputParameter name, InputParameter aliases, InputParameter descr, InputParameter perm, InputParameter permMessage, InputParameter usage) {
            super(CompCommand.this, name, aliases, descr, perm, permMessage, usage);
            this.name = name;
            this.aliases = aliases;
            this.descr = descr;
            this.perm = perm;
            this.permMessage = permMessage;
            this.usage = usage;

            getTab().textProperty().bind(Bindings
                    .when(name.getControl().textProperty().isNotEmpty())
                    .then(Bindings.concat("/", name.getControl().textProperty()))
                    .otherwise("Command"));
        }

        public String getName() {
            return name.toJava();
        }

        public String getAliases() {
            return aliases.toJava();
        }

        public String getDescription() {
            return descr.toJava();
        }

        public String getPermission() {
            return perm.toJava();
        }

        public String getPermissionMessage() {
            return permMessage.toJava();
        }

        public String getUsage() {
            return usage.toJava();
        }
    }
}
