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
import us.donut.visualbukkit.blocks.expressions.ExprFunction;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;

import java.util.List;
import java.util.StringJoiner;

public class FunctionPane extends MethodPane {

    public static void promptNew(Project project) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Function");
        dialog.setContentText("Function:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        String function = dialog.showAndWait().orElse("").replaceAll("\\s", "");
        if (!function.isEmpty()) {
            if (StringUtils.isAlphanumeric(function)) {
                for (FunctionPane functionPane : project.getFunctions()) {
                    if (functionPane.getMethodName().equalsIgnoreCase(function)) {
                        VisualBukkit.displayError("Function already exists");
                        return;
                    }
                }
                FunctionPane functionPane = new FunctionPane(project, function, promptParameters());
                project.add(functionPane);
                functionPane.open();
                project.getTabPane().getSelectionModel().select(functionPane);
            } else {
                VisualBukkit.displayError("Invalid function name");
            }
        }
    }

    public FunctionPane(Project project, String functionName, Class<?>... parameters) {
        super("Function", project, functionName, parameters);
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
                if (expression instanceof ExprFunction && equals(((ExprFunction) expression).getFunction())) {
                    expressionParameter.setExpression(null);
                } else if (expression != null) {
                    deleteUsages(expression.getParameters());
                }
            }
        }
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod functionMethod = mainClass.getDeclaredMethod("function");
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src =
                "if (function.equalsIgnoreCase(\"" + methodName + "\")) {" +
                "Map tempVariables = new HashMap();" +
                stringJoiner.toString() + "}";
        functionMethod.insertBefore(src);
    }
}
