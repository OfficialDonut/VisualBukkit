package us.donut.visualbukkit.blocks.expressions;

import javafx.scene.text.Text;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

public class ExprEmptyParameter extends ExpressionBlock implements ExpressionBlock.Changeable {

    private Class<?> returnType;

    public ExprEmptyParameter(Class<?> returnType) {
        this.returnType = returnType;
        getChildren().add(new Text("<" + TypeHandler.getUserFriendlyName(returnType) + ">"));
    }

    @Override
    protected SyntaxNode init() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toJava() {
        return "((" + returnType.getCanonicalName() + ") new Object())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return "";
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public void unload(ConfigurationSection section) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(ConfigurationSection section) {
        throw new UnsupportedOperationException();
    }
}
