package com.gmail.visualbukkit.blocks.structures;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import javafx.scene.layout.HBox;
import org.bukkit.event.inventory.InventoryType;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.Arrays;

@Name("Create GUI")
@Description("Creates a GUI")
public class StructCreateGUI extends StructureBlock {

    private static String[] inventoryTypes;

    static {
        inventoryTypes = Arrays.stream(InventoryType.values())
                .filter(InventoryType::isCreatable)
                .map(Enum::name)
                .sorted()
                .toArray(String[]::new);
    }

    public StructCreateGUI() {
        ChoiceParameter typeChoice = new ChoiceParameter(inventoryTypes);
        init("Create GUI");
        initLine("type:  ", typeChoice);
        initLine("ID:    ", new StringLiteralParameter());
        initLine("title: ", String.class);
        HBox sizeLine = initLine("size:  ", int.class);
        sizeLine.visibleProperty().bind(typeChoice.getSelectionModel().selectedItemProperty().isEqualTo("CHEST"));
        sizeLine.managedProperty().bind(sizeLine.visibleProperty());
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addPluginModules(PluginModule.GUI);
        MethodSource<JavaClassSource> enableMethod = context.getMainClass().getMethod("onEnable");
        enableMethod.setBody(enableMethod.getBody() +
                "GUIManager.getInstance().register(" + arg(1) + ", guiPlayer -> {" +
                "try {" +
                "Inventory gui = Bukkit.createInventory(new GUIIdentifier(" + arg(1) + ")," + (arg(0).equals("CHEST") ?
                    arg(3) + ",PluginMain.color(" + arg(2) + "));" :
                    "org.bukkit.event.inventory.InventoryType." + arg(0) + ",PluginMain.color(" + arg(1) + "));") +
                getChildJava() +
                "return gui;" +
                "} catch (Exception e) { e.printStackTrace(); return null; }});");
    }
}
