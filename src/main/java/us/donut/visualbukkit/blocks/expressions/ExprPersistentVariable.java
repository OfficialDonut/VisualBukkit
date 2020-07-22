package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.control.*;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.CenteredHBox;

import java.util.StringJoiner;

@Description({"A persistent variable", "Returns: object"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE, ModificationType.DELETE})
public class ExprPersistentVariable extends ModifiableExpressionBlock<Object> {

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
        return "VariableManager.getVarValue(true," + getVarArgs() + ")";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return "VariableManager.setVarValue(true," + delta + "," + getVarArgs() + ");";
            case ADD: return "VariableManager.addToVar(true," + delta + "," + getVarArgs() + ");";
            case REMOVE: return "VariableManager.removeFromVar(true," + delta + "," + getVarArgs() + ");";
            case DELETE: return "VariableManager.deleteVar(true," + getVarArgs() + ");" ;
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
        syntaxNode.add("variable(");
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
