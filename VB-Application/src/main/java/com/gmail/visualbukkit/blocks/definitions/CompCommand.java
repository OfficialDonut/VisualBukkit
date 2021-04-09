package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import javafx.beans.binding.Bindings;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.regex.Pattern;

public class CompCommand extends PluginComponent {

    private final static Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\S*");

    public CompCommand() {
        super("comp-command");
    }

    @Override
    public Block createBlock() {
        InputParameter nameParameter = new InputParameter();
        nameParameter.textProperty().addListener(((o, oldValue, newValue) -> {
            if (!WHITE_SPACE_PATTERN.matcher(newValue).matches()) {
                nameParameter.setText(oldValue);
            }
        }));

        Block block = new Block(this, nameParameter, new InputParameter(), new InputParameter(), new InputParameter(), new InputParameter()) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                MethodSource<JavaClassSource> commandMethod = buildContext.getMainClass().getMethod("onCommand", "CommandSender", "Command", "String", "String[]");
                commandMethod.setBody(
                        "if (command.getName().equalsIgnoreCase(\"" + StringEscapeUtils.escapeJava(arg(0)) + "\")) {" +
                        buildContext.getLocalVariableDeclarations() +
                        "try {" +
                        getChildJava() +
                        "} catch (Exception e) { e.printStackTrace(); }}" +
                        commandMethod.getBody());
            }
        };

        block.getTab().textProperty().bind(Bindings
                .when(nameParameter.textProperty().isNotEmpty())
                .then(Bindings.concat("/", nameParameter.textProperty()))
                .otherwise("/<cmd>"));

        return block;
    }
}
