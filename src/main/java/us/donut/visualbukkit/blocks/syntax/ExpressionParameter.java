package us.donut.visualbukkit.blocks.syntax;

import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.plugin.PluginBuilder;

import java.util.Collections;
import java.util.List;

public class ExpressionParameter extends VBox implements BlockParameter, BlockContainer {

    private Class<?> returnType;
    private ExpressionBlock<?> expression;
    private EmptyExpressionBlock<?> emptyExprBlock;
    private Text emptyText;

    public ExpressionParameter(Class<?> returnType) {
        getStyleClass().add("expression-parameter-empty");
        this.returnType = returnType;
        emptyExprBlock = new EmptyExpressionBlock<>(returnType);
        getChildren().add(emptyText = new Text("<" + TypeHandler.getUserFriendlyName(returnType) + ">"));
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
            ExpressionBlock<?> newExpression = (ExpressionBlock<?>) block;
            ExpressionBlock<?> currentExpression = getExpression();
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
        setExpression((ExpressionBlock<?>) block);
        Platform.runLater(block::onDragDrop);
    }

    public void setExpression(ExpressionBlock<?> expression) {
        getChildren().clear();
        if (expression == null || expression instanceof EmptyExpressionBlock) {
            this.expression = null;
            getChildren().add(emptyText);
            getStyleClass().set(0, "expression-parameter-empty");
        } else {
            getChildren().add(this.expression = expression);
            getStyleClass().set(0, "expression-parameter-filled");
        }
    }

    public boolean isEmpty() {
        return expression == null;
    }

    @Override
    public String toJava() {
        return TypeHandler.convert(getExpression().getReturnType(), returnType, getExpression().toJava());
    }

    @Override
    public void unload(ConfigurationSection section) {
        section.set("block-type", getExpression().getClass().getCanonicalName());
        getExpression().unload(section);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(ConfigurationSection section) throws Exception {
        String className = section.getString("block-type");
        if (className != null) {
            Class<? extends ExpressionBlock<?>> blockType = (Class<? extends ExpressionBlock<?>>) Class.forName(className);
            BlockInfo<?> blockInfo = BlockRegistry.getInfo(blockType);
            if (blockInfo != null) {
                ExpressionBlock<?> expression = (ExpressionBlock<?>) blockInfo.createBlock();
                expression.load(section);
                setExpression(expression);
            }
        }
    }

    @Override
    public List<ExpressionBlock<?>> getBlocks(boolean ignoreDisabled) {
        return expression == null ? Collections.emptyList() : Collections.singletonList(expression);
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public ExpressionBlock<?> getExpression() {
        return expression != null ? expression : emptyExprBlock;
    }
}
