package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.ProcedurePane;
import us.donut.visualbukkit.editor.ProjectManager;

import java.util.List;
import java.util.StringJoiner;

@Description({"Gets a procedure runnable", "Returns: runnable"})
public class ExprProcedureRunnable extends ExpressionBlock<Runnable> {

    private ProcedurePane procedure;

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("procedure <...>");
    }

    @Override
    public void onDragDrop() {
        if (procedure == null) {
            List<ProcedurePane> procedures = getBlockPane().getProject().getProcedures();
            if (!procedures.isEmpty()) {
                ChoiceDialog<ProcedurePane> dialog = new ChoiceDialog<>();
                dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
                dialog.setTitle("Select procedure");
                dialog.setContentText("Procedure:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                for (ProcedurePane procedurePane : procedures) {
                    dialog.getItems().add(procedurePane);
                }
                dialog.setSelectedItem(dialog.getItems().get(0));
                setProcedure(dialog.showAndWait().orElse(dialog.getItems().get(0)));
            } else {
                ((ExpressionParameter) getParent()).setExpression(null);
                VisualBukkit.displayError("No procedures have been defined");
            }
        }
    }

    @Override
    public String toJava() {
        if (procedure != null) {
            if (!getParameters().isEmpty()) {
                StringJoiner joiner = new StringJoiner(",");
                getParameters().forEach(parameter -> joiner.add(parameter.toJava()));
                return "getRunnable(\"" + procedure.getProcedure() + "\", new Object[]{" + joiner.toString() + "})";
            } else {
                return "getRunnable(\"" + procedure.getProcedure() + "\", new Object[0])";
            }
        }
        return "getRunnable(null, null)";
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("procedure", procedure.getProcedure());
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        for (ProcedurePane procedure : ProjectManager.getCurrentProject().getProcedures()) {
            if (procedure.getProcedure().equalsIgnoreCase(section.getString("procedure"))) {
                setProcedure(procedure);
            }
        }
        super.load(section);
    }

    public ProcedurePane getProcedure() {
        return procedure;
    }

    private void setProcedure(ProcedurePane procedure) {
        this.procedure = procedure;
        getSyntaxNode().getChildren().clear();
        getSyntaxNode().add(procedure.getProcedure() + "(");
        Class<?>[] parameters = procedure.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            getSyntaxNode().add(parameters[i]);
            if (i != parameters.length - 1) {
                getSyntaxNode().add(",");
            }
        }
        getSyntaxNode().add(")");
    }
}
