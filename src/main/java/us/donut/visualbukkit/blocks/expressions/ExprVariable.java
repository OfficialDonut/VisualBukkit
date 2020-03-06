package us.donut.visualbukkit.blocks.expressions;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.StringJoiner;

@Description({"A variable", "Returns: object"})
public class ExprVariable extends ExpressionBlock implements ExpressionBlock.Changeable {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("<...>");
    }

    @Override
    public void onDragDrop() {
        if (getParameters().size() == 0) {
            Dialog<Integer> dialog = new Dialog<>();
            Spinner<Integer> spinner = new Spinner<>();
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
            HBox content = new HBox(new Label("Number of arguments: "), spinner);
            content.setAlignment(Pos.CENTER_LEFT);
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.setResultConverter(buttonType -> spinner.getValue());
            setNumArgs(dialog.showAndWait().orElse(2));
        }
    }

    @Override
    public String toJava() {
        return "variables.get(" + getVariable() + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "variables.put(" + getVariable() + "," + delta + ");";
            case ADD: return "addToVariable(" + getVariable() + "," + delta + ", variables);";
            case REMOVE: return "removeFromVariable(" + getVariable() + "," + delta + ", variables);";
            case DELETE: return "variables.remove(" + getVariable() + ");" ;
            default: return null;
        }
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("num-args", getParameters().size());
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        setNumArgs(section.getInt("num-args"));
        super.load(section);
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    private void setNumArgs(int num) {
        getSyntaxNode().getChildren().clear();
        getSyntaxNode().add("variable(");
        for (int i = 0; i < num; i++) {
            getSyntaxNode().add(Object.class);
            if (i != num - 1) {
                getSyntaxNode().add(",");
            }
        }
        getSyntaxNode().add(")");
    }

    private String getVariable() {
        if (!getParameters().isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            getParameters().forEach(parameter -> joiner.add(parameter.toJava()));
            return "getVariable(new Object[]{" + joiner.toString() + "})";
        } else {
            return "null";
        }
    }
}
