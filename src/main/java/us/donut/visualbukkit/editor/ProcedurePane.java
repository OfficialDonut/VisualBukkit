package us.donut.visualbukkit.editor;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.expressions.ExprProcedureRunnable;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;

import java.util.*;

public class ProcedurePane extends BlockPane {

    public static void promptNew(Project project) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Procedure");
        dialog.setContentText("Procedure:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        String procedure = dialog.showAndWait().orElse("").replaceAll("\\s", "");
        if (!procedure.isEmpty()) {
            if (StringUtils.isAlphanumeric(procedure)) {
                for (ProcedurePane procedurePane : project.getProcedures()) {
                    if (procedurePane.getProcedure().equalsIgnoreCase(procedure)) {
                        VisualBukkit.displayError("Procedure already exists");
                        return;
                    }
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Parameters");
                alert.setHeaderText(null);
                alert.setGraphic(null);
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
                ProcedurePane procedurePane = new ProcedurePane(project, procedure, parameters);
                project.add(procedurePane);
                procedurePane.open();
                project.getTabPane().getSelectionModel().select(procedurePane);
            } else {
                VisualBukkit.displayError("Invalid procedure name");
            }
        }
    }

    private String procedure;
    private Class<?>[] parameters;

    public ProcedurePane(Project project, String procedure, Class<?>... parameters) {
        super(project, procedure + "()");
        this.procedure = procedure;
        this.parameters = parameters;

        StringJoiner joiner = new StringJoiner(", ");
        for (Class<?> parameter : parameters) {
            joiner.add(TypeHandler.getAlias(parameter));
        }
        Label label = new Label("Procedure: " + procedure + "(" + joiner.toString() + ")");
        label.getStyleClass().add("block-pane-label");

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this procedure?\n(all usages will be deleted)");
            confirmation.setHeaderText(null);
            confirmation.setGraphic(null);
            if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                project.remove(this);
                for (BlockPane blockPane : project.getBlockPanes()) {
                    if (!equals(blockPane)) {
                        deleteUsages(blockPane.getBlockArea());
                    }
                }
                VisualBukkit.displayMessage("Successfully deleted procedure");
            }
        });

        getInfoArea().getChildren().addAll(label, deleteButton);
    }

    private void deleteUsages(Pane pane) {
        for (Node child : pane.getChildren()) {
            if (child instanceof StatementBlock) {
                deleteUsages(((StatementBlock) child).getParameters());
                if (child instanceof ParentBlock) {
                    deleteUsages((ParentBlock) child);
                }
            }
        }
    }

    private void deleteUsages(List<BlockParameter> parameters) {
        for (BlockParameter parameter : parameters) {
            if (parameter instanceof ExpressionParameter) {
                ExpressionParameter expressionParameter = (ExpressionParameter) parameter;
                ExpressionBlock<?> expression = expressionParameter.getExpression();
                if (expression instanceof ExprProcedureRunnable && equals(((ExprProcedureRunnable) expression).getProcedure())) {
                    expressionParameter.setExpression(null);
                } else if (expression != null) {
                    deleteUsages(expression.getParameters());
                }
            }
        }
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod procedureMethod = mainClass.getDeclaredMethod("procedure");
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src =
                "if (procedure.equalsIgnoreCase(\"" + procedure + "\")) {" +
                "Map tempVariables = new HashMap();" +
                stringJoiner.toString() + "}";
        procedureMethod.insertBefore(src);
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        List<String> parameters = new ArrayList<>();
        for (Class<?> parameter : this.parameters) {
            parameters.add(TypeHandler.getAlias(parameter));
        }
        section.set("parameters", parameters);
    }

    public String getProcedure() {
        return procedure;
    }

    public Class<?>[] getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return procedure;
    }
}
