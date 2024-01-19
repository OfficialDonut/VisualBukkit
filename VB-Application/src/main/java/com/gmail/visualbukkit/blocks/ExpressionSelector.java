package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.PopOverSelector;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;

public class ExpressionSelector extends PopOverSelector<BlockFactory<ExpressionBlock>> {

    public ExpressionSelector(ClassInfo type) {
        getStyleClass().add("expression-parameter");
        setPromptText("<" + type.getSimpleName() + ">");
        setTooltip(new Tooltip(type.getName()));

        setCellSupplier(() -> new ListCell<>() {
            @Override
            protected void updateItem(BlockFactory<ExpressionBlock> item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.toString());
                    setTooltip(new Tooltip(item.getBlockDefinition().description()));
                    if (item.isPinned()) {
                        setGraphic(new FontIcon(LineAwesomeSolid.THUMBTACK));
                        setContextMenu(new ContextMenu(new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.unpin"), e -> {
                            BlockRegistry.setPinned(item, false);
                            getItemList().setAll(BlockRegistry.getExpressions());
                        })));
                    } else {
                        setGraphic(null);
                        setContextMenu(new ContextMenu(new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.pin"), e -> {
                            BlockRegistry.setPinned(item, true);
                            getItemList().setAll(BlockRegistry.getExpressions());
                        })));
                    }
                } else {
                    setText("");
                    setTooltip(null);
                }
            }
        });
    }

    @Override
    public void open() {
        getItemList().setAll(BlockRegistry.getExpressions());
        super.open();
    }
}
