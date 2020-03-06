package us.donut.visualbukkit.editor;

import javafx.scene.control.*;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang.StringUtils;
import us.donut.visualbukkit.VisualBukkit;

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

        getInfoArea().getChildren().addAll(label, renameButton, deleteButton);
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

    public String getCommand() {
        return command;
    }
}
