package us.donut.visualbukkit.blocks.syntax;

import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.expressions.ExprEmptyParameter;
import us.donut.visualbukkit.plugin.PluginBuilder;

import java.util.Collections;
import java.util.List;

public class ExpressionParameter extends VBox implements BlockParameter, BlockContainer {

    private Class<?> returnType;
    private ExpressionBlock expression;
    private ExprEmptyParameter emptyExpr;

    public ExpressionParameter(Class<?> returnType) {
        getStyleClass().add("expression-parameter-empty");
        this.returnType = returnType;
        getChildren().add(expression = emptyExpr = new ExprEmptyParameter(returnType));
        DragManager.enableBlockContainer(this);
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

    @Override
    public boolean canAccept(CodeBlock block, double yCoord) {
        boolean valid = false;
        if (block instanceof ExpressionBlock) {
            ExpressionParameter parent = (ExpressionParameter) block.getParent();
            ExpressionBlock newExpression = (ExpressionBlock) block;
            ExpressionBlock currentExpression = expression;
            if (parent != null) {
                parent.setExpression(null);
            }
            setExpression(newExpression);
            valid = PluginBuilder.isCodeValid(block.getBlockPane());
            setExpression(currentExpression);
            if (parent != null) {
                parent.setExpression(newExpression);
            }
        }
        return valid;
    }

    @Override
    public void accept(CodeBlock block, double yCoord) {
        UndoManager.capture();
        ExpressionParameter parent = (ExpressionParameter) block.getParent();
        if (parent != null) {
            parent.setExpression(null);
        }
        setExpression((ExpressionBlock) block);
        Platform.runLater(block::onDragDrop);
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

    @Override
    public List<ExpressionBlock> getBlocks(boolean ignoreDisabled) {
        return expression == null ? Collections.emptyList() : Collections.singletonList(expression);
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public ExpressionBlock getExpression() {
        return expression;
    }
}
