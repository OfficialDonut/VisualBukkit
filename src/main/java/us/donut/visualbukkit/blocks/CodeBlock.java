package us.donut.visualbukkit.blocks;

import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.editor.SelectorPane;
import us.donut.visualbukkit.util.Loadable;

import java.util.List;

public abstract class CodeBlock extends VBox implements Loadable {

    protected SyntaxNode syntaxNode;
    protected ContextMenu contextMenu;

    public CodeBlock() {
        DragManager.enableDragging(this);
        contextMenu = new ContextMenu();
        setOnContextMenuRequested(e -> {
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
            e.consume();
        });
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem deleteItem = new MenuItem("Delete");
        copyItem.setOnAction(e -> CopyPasteManager.copy(this));
        cutItem.setOnAction(e -> {
            copyItem.getOnAction().handle(e);
            deleteItem.getOnAction().handle(e);
        });
        deleteItem.setOnAction(e -> {
            SelectorPane selectorPane = VisualBukkit.getInstance().getSelectorPane();
            if (selectorPane.canAccept(this, -1)) {
                selectorPane.accept(this, -1);
            } else {
                VisualBukkit.displayError("Cannot delete block");
            }
        });
        contextMenu.getItems().addAll(copyItem, cutItem, deleteItem);
        getChildren().add(syntaxNode = init());
    }

    protected abstract SyntaxNode init();

    public void onDragDrop() {}

    public abstract String toJava();

    @Override
    public void unload(ConfigurationSection section) {
        List<BlockParameter> parameters = getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).unload(section.createSection("parameters." + i));
        }
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        ConfigurationSection parameterSection = section.getConfigurationSection("parameters");
        if (parameterSection != null) {
            List<BlockParameter> parameters = getParameters();
            for (int i = 0; i < parameters.size(); i++) {
                parameters.get(i).load(parameterSection.getConfigurationSection(String.valueOf(i)));
            }
        }
    }

    public String arg(int i) {
        return getParameter(i).toJava();
    }

    public List<BlockParameter> getParameters() {
        return syntaxNode.getParameters();
    }

    public BlockParameter getParameter(int i) {
        return getParameters().get(i);
    }

    public SyntaxNode getSyntaxNode() {
        return syntaxNode;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public BlockPane getBlockPane() {
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof BlockPane.BlockArea) {
                return ((BlockPane.BlockArea) parent).getBlockPane();
            }
            parent = parent.getParent();
        }
        return null;
    }
}
