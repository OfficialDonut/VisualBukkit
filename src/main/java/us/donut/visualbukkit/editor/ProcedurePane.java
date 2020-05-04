package us.donut.visualbukkit.editor;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang.StringUtils;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.expressions.ExprProcedureRunnable;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;

import java.util.List;
import java.util.StringJoiner;

public class ProcedurePane extends MethodPane {

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
                    if (procedurePane.getMethodName().equalsIgnoreCase(procedure)) {
                        VisualBukkit.displayError("Procedure already exists");
                        return;
                    }
                }
                ProcedurePane procedurePane = new ProcedurePane(project, procedure, promptParameters());
                project.add(procedurePane);
                procedurePane.open();
                project.getTabPane().getSelectionModel().select(procedurePane);
            } else {
                VisualBukkit.displayError("Invalid procedure name");
            }
        }
    }

    public ProcedurePane(Project project, String procedureName, Class<?>... parameters) {
        super("Procedure", project, procedureName, parameters);
        getInfoArea().getChildren().addAll(titleLabel, deleteButton, changeParamButton);
    }

    @Override
    protected void deleteUsages(Pane pane) {
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
                "if (procedure.equalsIgnoreCase(\"" + methodName + "\")) {" +
                "Map tempVariables = new HashMap();" +
                stringJoiner.toString() + "}";
        procedureMethod.insertBefore(src);
    }
}
