package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.control.*;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.CenteredHBox;

import java.util.StringJoiner;

@Name("Non-Persistent Variable")
@Description({"A non-persistent variable", "Changers: set, delete, clear, add, remove", "Returns: object"})
public class ExprNonPersistentVariable extends ChangeableExpressionBlock<Object> {

    private int numArgs = -1;

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("<...>");
    }

    @Override
    public void onDragDrop() {
        if (numArgs < 1) {
            Dialog<Integer> dialog = new Dialog<>();
            Spinner<Integer> spinner = new Spinner<>();
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
            dialog.getDialogPane().setContent(new CenteredHBox(new Label("Number of arguments: "), spinner));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.setResultConverter(buttonType -> spinner.getValue());
            setNumArgs(dialog.showAndWait().orElse(2));
        }
    }

    @Override
    public String toJava() {
        return "VariableManager.getVarValue(false," + getVarArgs() + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "VariableManager.setVarValue(false," + delta + "," + getVarArgs() + ");";
            case ADD: return "VariableManager.addToVar(false," + delta + "," + getVarArgs() + ");";
            case REMOVE: return "VariableManager.removeFromVar(false," + delta + "," + getVarArgs() + ");";
            case DELETE: case CLEAR: return "VariableManager.deleteVar(false," + getVarArgs() + ");" ;
            default: return null;
        }
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("num-args", numArgs);
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        setNumArgs(section.getInt("num-args"));
        super.load(section);
    }

    private void setNumArgs(int numArgs) {
        this.numArgs = numArgs;
        syntaxNode.clear();
        syntaxNode.add("temp variable(");
        for (int i = 0; i < numArgs; i++) {
            syntaxNode.add(Object.class);
            if (i != numArgs - 1) {
                syntaxNode.add(",");
            }
        }
        syntaxNode.add(")");
    }

    private String getVarArgs() {
        if (!getParameters().isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            getParameters().forEach(parameter -> joiner.add(parameter.toJava()));
            return "new Object[]{" + joiner.toString() + "}";
        } else {
            return "null";
        }
    }
}
