package us.donut.visualbukkit.blocks;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.plugin.PluginBuilder;

public abstract class StatementBlock extends CodeBlock {

    private ContextMenu disabledContextMenu;
    private boolean disabled;

    public StatementBlock() {
        getStyleClass().add("statement-block");
        MenuItem disableItem = new MenuItem("Disable");
        MenuItem enableItem = new MenuItem("Enable");
        getContextMenu().getItems().add(disableItem);
        disabledContextMenu = new ContextMenu(enableItem);
        disableItem.setOnAction(e -> disable());
        enableItem.setOnAction(e -> enable());
        setOnContextMenuRequested(e -> {
            (disabled ? disabledContextMenu : getContextMenu()).show(this, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("disabled", disabled);
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        super.load(section);
        if (section.getBoolean("disabled")) {
            disable();
        }
    }

    public boolean isEnabled() {
        return !disabled;
    }

    public ContextMenu getDisabledContextMenu() {
        return disabledContextMenu;
    }

    private void disable() {
        disabled = true;
        if (PluginBuilder.isCodeValid(getBlockPane())) {
            UndoManager.capture();
            setOpacity(0.5);
        } else {
            disabled = false;
            VisualBukkit.displayError("Cannot disable this block");
        }
    }

    private void enable() {
        disabled = false;
        if (PluginBuilder.isCodeValid(getBlockPane())) {
            UndoManager.capture();
            setOpacity(1);
        } else {
            disabled = true;
            VisualBukkit.displayError("Cannot enable this block");
        }
    }
}
