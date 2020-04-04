package us.donut.visualbukkit.editor;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.util.CenteredHBox;

import java.util.StringJoiner;

public class CommandPane extends BlockPane {

    public static void promptNew(Project project) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Command");
        dialog.setContentText("Command:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        String command = dialog.showAndWait().orElse("").replaceAll("\\s", "");
        if (!command.isEmpty()) {
            if (StringUtils.isAlphanumeric(command)) {
                for (CommandPane commandPane : project.getCommands()) {
                    if (commandPane.getCommand().equalsIgnoreCase(command)) {
                        VisualBukkit.displayError("Command already exists");
                        return;
                    }
                }
                CommandPane commandPane = new CommandPane(project, command);
                project.add(commandPane);
                commandPane.open();
                project.getTabPane().getSelectionModel().select(commandPane);
            } else {
                VisualBukkit.displayError("Invalid command name");
            }
        }
    }

    private String command;
    private TextField descField = new TextField();
    private TextField aliasesField = new TextField();
    private TextField permField = new TextField();
    private TextField permMessageField = new TextField();
    private TextField usageField = new TextField();

    public CommandPane(Project project, String command) {
        super(project, "/" + command);
        this.command = command;

        Label label = new Label("Command: /" + command);
        label.getStyleClass().add("block-pane-label");

        Button renameButton = new Button("Rename");
        renameButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Rename Command");
            dialog.setContentText("New command:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            String newCommand = dialog.showAndWait().orElse("").replaceAll("\\s", "");
            if (!newCommand.isEmpty()) {
                if (StringUtils.isAlphanumeric(newCommand)) {
                    for (CommandPane commandPane : project.getCommands()) {
                        if (commandPane.getCommand().equalsIgnoreCase(newCommand)) {
                            VisualBukkit.displayError("Command already exists");
                            return;
                        }
                    }
                    this.command = newCommand;
                    setText("/" + newCommand);
                    label.setText("Command: /" + newCommand);
                    getProjectStructureLabel().setText(getText());
                } else {
                    VisualBukkit.displayError("Invalid command name");
                }
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this command?");
            confirmation.setHeaderText(null);
            confirmation.setGraphic(null);
            if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                project.remove(this);
                VisualBukkit.displayMessage("Successfully deleted command");
            }
        });

        getInfoArea().getChildren().add(new VBox(5,
                new CenteredHBox(15, label, renameButton, deleteButton),
                new CenteredHBox(new Label("Description:\t"), descField, new Label("\tPermission:\t\t"), permField, new Label("\tUsage:\t"), usageField),
                new CenteredHBox(new Label("Aliases:\t\t"), aliasesField, new Label("\tPerm Message:\t"), permMessageField)));
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod commandMethod = mainClass.getDeclaredMethod("onCommand");
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src =
                "if (command.getName().equalsIgnoreCase(\"" + command + "\")) {" +
                "Map tempVariables = new HashMap();" +
                stringJoiner.toString() + "}";
        commandMethod.insertBefore(src);
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("description", descField.getText());
        section.set("aliases", aliasesField.getText());
        section.set("permission", permField.getText());
        section.set("permission-message", permMessageField.getText());
        section.set("usage", usageField.getText());
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        super.load(section);
        descField.setText(section.getString("description", ""));
        aliasesField.setText(section.getString("aliases", ""));
        permField.setText(section.getString("permission", ""));
        permMessageField.setText(section.getString("permission-message", ""));
        usageField.setText(section.getString("usage", ""));
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return descField.getText();
    }

    public String getAliases() {
        return aliasesField.getText();
    }

    public String getPermission() {
        return permField.getText();
    }

    public String getPermMessage() {
        return permMessageField.getText();
    }

    public String getUsage() {
        return usageField.getText();
    }
}
