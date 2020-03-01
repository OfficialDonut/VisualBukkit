package us.donut.visualbukkit.blocks.syntax;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.expressions.ExprEmptyParameter;

public class ExpressionParameter extends VBox implements BlockParameter {

    private Class<?> returnType;
    private ExpressionBlock expression;
    private ExprEmptyParameter emptyExpr;

    public ExpressionParameter(Class<?> returnType) {
        getStyleClass().add("expression-parameter-empty");
        this.returnType = returnType;
        getChildren().add(expression = emptyExpr = new ExprEmptyParameter(returnType));
        DragManager.enableDragTarget(this);
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> CopyPasteManager.paste(this, -1));
        ContextMenu contextMenu = new ContextMenu(pasteItem);
        setOnContextMenuRequested(e -> {
            if (isEmpty()) {
                contextMenu.show(this, e.getScreenX(), e.getScreenY());
                e.consume();
            }
        });
    }

    public void setExpression(ExpressionBlock expression) {
        this.expression = expression != null ? expression : emptyExpr;
        getChildren().clear();
        getChildren().add(this.expression);
        getStyleClass().set(0, isEmpty() ? "expression-parameter-empty" : "expression-parameter-filled");
    }

    public boolean isEmpty() {
        return expression instanceof ExprEmptyParameter;
    }

    @Override
    public String toJava() {
        return TypeHandler.convert(expression.getReturnType(), returnType, expression.toJava());
    }

    @Override
    public void unload(ConfigurationSection section) {
        if (!isEmpty()) {
            String className = expression.getClass().getCanonicalName().replace('.', '_');
            expression.unload(section.createSection(className));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(ConfigurationSection section) throws Exception {
        for (String key : section.getKeys(false)) {
            String className = key.replace('_', '.');
            Class<? extends ExpressionBlock> blockClass = (Class<? extends ExpressionBlock>) Class.forName(className);
            ExpressionBlock expression = BlockRegistry.getInfo(blockClass).createBlock();
            expression.load(section.getConfigurationSection(key));
            setExpression(expression);
            break;
        }
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public ExpressionBlock getExpression() {
        return expression;
    }
}
