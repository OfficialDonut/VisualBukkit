package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.control.*;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.CenteredHBox;
import us.donut.visualbukkit.util.SimpleList;

import java.util.StringJoiner;

@Description({"A list of objects", "Returns: list"})
public class ExprList extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("<...>");
    }

    @Override
    public void onDragDrop() {
        if (getParameters().isEmpty()) {
            Dialog<Integer> dialog = new Dialog<>();
            Spinner<Integer> spinner = new Spinner<>();
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
            dialog.getDialogPane().setContent(new CenteredHBox(new Label("Size of list: "), spinner));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.setResultConverter(buttonType -> spinner.getValue());
            setSize(dialog.showAndWait().orElse(1));
        }
    }

    @Override
    public String toJava() {
        if (!getParameters().isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            getParameters().forEach(parameter -> joiner.add(parameter.toJava()));
            return "new SimpleList(new Object[]{" + joiner.toString() + "})";
        } else {
            return "new SimpleList(new Object[0])";
        }
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("size", getParameters().size());
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        setSize(section.getInt("size"));
        super.load(section);
    }

    private void setSize(int size) {
        getSyntaxNode().getChildren().clear();
        for (int i = 0; i < size; i++) {
            getSyntaxNode().add(Object.class);
            if (i != size - 1) {
                getSyntaxNode().add(",");
            }
        }
    }
}
