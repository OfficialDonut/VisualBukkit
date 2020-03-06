package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.FunctionPane;
import us.donut.visualbukkit.editor.ProjectManager;

import java.util.List;
import java.util.StringJoiner;

@Name("Function Value")
@Description("The return value of a function")
public class ExprFunction extends ExpressionBlock {

    private FunctionPane function;

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("<...>");
    }

    @Override
    public void onDragDrop() {
        if (function == null) {
            List<FunctionPane> functions = getBlockPane().getProject().getFunctions();
            if (!functions.isEmpty()) {
                ChoiceDialog<FunctionPane> dialog = new ChoiceDialog<>();
                dialog.setTitle("Select function");
                dialog.setContentText("Function:");
                dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                for (FunctionPane functionPane : functions) {
                    dialog.getItems().add(functionPane);
                }
                dialog.setSelectedItem(dialog.getItems().get(0));
                setFunction(dialog.showAndWait().orElse(dialog.getItems().get(0)));
            } else {
                ((ExpressionParameter) getParent()).setExpression(null);
                VisualBukkit.displayError("No functions have been defined");
            }
        }
    }

    @Override
    public String toJava() {
        if (function != null) {
            if (!getParameters().isEmpty()) {
                StringJoiner joiner = new StringJoiner(",");
                getParameters().forEach(parameter -> joiner.add(parameter.toJava()));
                return "this.function(\"" + function.getFunction() + "\", new Object[]{" + joiner.toString() + "})";
            } else {
                return "this.function(\"" + function.getFunction() + "\", new Object[0])";
            }
        }
        return "new Object()";
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("function", function.getFunction());
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        for (FunctionPane function : ProjectManager.getCurrentProject().getFunctions()) {
            if (function.getFunction().equalsIgnoreCase(section.getString("function"))) {
                setFunction(function);
            }
        }
        super.load(section);
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    public FunctionPane getFunction() {
        return function;
    }

    private void setFunction(FunctionPane function) {
        this.function = function;
        getSyntaxNode().getChildren().clear();
        getSyntaxNode().add(function.getFunction() + "(");
        Class<?>[] parameters = function.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            getSyntaxNode().add(parameters[i]);
            if (i != parameters.length - 1) {
                getSyntaxNode().add(",");
            }
        }
        getSyntaxNode().add(")");
    }
}
