package us.donut.visualbukkit.editor;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.util.TitleLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public abstract class MethodPane extends BlockPane {

    protected String methodName;
    protected Class<?>[] parameters;
    protected TitleLabel titleLabel;
    protected Button deleteButton;
    protected Button changeParamButton;

    public MethodPane(String type, Project project, String methodName, Class<?>... parameters) {
        super(project, methodName + "()");
        this.methodName = methodName;
        this.parameters = parameters;
        titleLabel = new TitleLabel(type + ": " + methodName + "(" + getParametersString() + ")", 2);
        deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this?\n(all usages will be deleted)");
            confirmation.setHeaderText(null);
            confirmation.setGraphic(null);
            if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                project.remove(this);
                for (BlockPane blockPane : project.getBlockPanes()) {
                    if (!equals(blockPane)) {
                        deleteUsages(blockPane.getBlockArea());
                    }
                }
                VisualBukkit.displayMessage("Successfully deleted");
            }
        });

        changeParamButton = new Button("Change Parameters");
        changeParamButton.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change parameters?\n(all usages will be deleted)");
            confirmation.setHeaderText(null);
            confirmation.setGraphic(null);
            if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                this.parameters = promptParameters();
                titleLabel.setText(type + ": " + methodName + "(" + getParametersString() + ")");
                for (BlockPane blockPane : project.getBlockPanes()) {
                    deleteUsages(blockPane.getBlockArea());
                }
            }
        });
    }

    protected abstract void deleteUsages(Pane pane);

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        List<String> parameters = new ArrayList<>();
        for (Class<?> parameter : this.parameters) {
            parameters.add(TypeHandler.getAlias(parameter));
        }
        section.set("parameters", parameters);
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameters() {
        return parameters;
    }

    public String getParametersString() {
        StringJoiner joiner = new StringJoiner(", ");
        for (Class<?> parameter : parameters) {
            joiner.add(TypeHandler.getAlias(parameter));
        }
        return joiner.toString();
    }

    @Override
    public String toString() {
        return methodName;
    }

    protected static Class<?>[] promptParameters() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Parameters");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        ListView<String> parameterList = new ListView<>();
        parameterList.setPlaceholder(new Label("<no parameters>"));
        parameterList.setPrefHeight(200);
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
            choiceDialog.setTitle("Select type");
            choiceDialog.setContentText("Type:");
            choiceDialog.setHeaderText(null);
            choiceDialog.setGraphic(null);
            choiceDialog.getItems().addAll(TypeHandler.getAliases());
            Optional<String> choice = choiceDialog.showAndWait();
            choice.ifPresent(s -> parameterList.getItems().add(s));
        });
        Button moveUpButton = new Button("Move up");
        moveUpButton.setOnAction(e -> {
            int i = parameterList.getSelectionModel().getSelectedIndex();
            if (i > 0) {
                parameterList.getItems().add(i - 1, parameterList.getItems().remove(i));
                parameterList.getSelectionModel().select(i - 1);
            }
        });
        Button moveDownButton = new Button("Move down");
        moveDownButton.setOnAction(e -> {
            int i = parameterList.getSelectionModel().getSelectedIndex();
            if (i != parameterList.getItems().size() -1) {
                parameterList.getItems().add(i + 1, parameterList.getItems().remove(i));
                parameterList.getSelectionModel().select(i + 1);
            }
        });
        Button removeButton = new Button("Delete");
        removeButton.setOnAction(e -> {
            int i = parameterList.getSelectionModel().getSelectedIndex();
            if (i != -1) {
                parameterList.getItems().remove(i);
            }
        });
        VBox content = new VBox(5, new Label("Parameter types:"), parameterList, new HBox(3, addButton, moveUpButton, moveDownButton, removeButton));
        alert.getDialogPane().setContent(content);
        alert.showAndWait();

        Class<?>[] parameters = new Class[parameterList.getItems().size()];
        for (int i = 0; i < parameterList.getItems().size(); i++) {
            parameters[i] = TypeHandler.getType(parameterList.getItems().get(i));
        }
        return parameters;
    }
}
