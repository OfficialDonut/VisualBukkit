package us.donut.visualbukkit.blocks.structures;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.bukkit.event.inventory.InventoryType;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.util.ComboBoxView;
import us.donut.visualbukkit.util.DataConfig;

import java.util.Arrays;
import java.util.Comparator;

@Name("GUI")
@Description("Defines a GUI")
public class StructGUI extends StructureBlock {

    private static ComboBoxView<InventoryType> typeComboBoxView = new ComboBoxView<>();

    static {
        typeComboBoxView.getComboBox().getItems().addAll(Arrays.stream(InventoryType.values())
                .filter(InventoryType::isCreatable)
                .sorted(Comparator.comparing(Enum::name))
                .toArray(InventoryType[]::new));
        typeComboBoxView.getStylesheets().add("/style.css");
    }

    private InventoryType type;
    private Text typeText;

    @Override
    protected Syntax init() {
        return new Syntax(typeText = new Text("<...>"), "GUI", new StringLiteralParameter(), "named", String.class);
    }

    @Override
    public void update() {
        super.update();
        if (type == null) {
            typeComboBoxView.getComboBox().getSelectionModel().select(InventoryType.CHEST);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New GUI");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.getDialogPane().setContent(new HBox(5, new Text("Type:"), typeComboBoxView));
            alert.getDialogPane().setPrefWidth(300);
            Platform.runLater(() -> {
                alert.showAndWait();
                type = typeComboBoxView.getComboBox().getValue();
                typeText.setText(type.name());
                if (type == InventoryType.CHEST) {
                    ExpressionParameter sizeParameter = new ExpressionParameter(int.class);
                    getParameters().add(sizeParameter);
                    getSyntaxLine(0).getChildren().addAll(new Text("with size"), sizeParameter);
                }
            });
        }
    }

    @Override
    public void insertInto(JavaClassSource mainClass) {
        BuildContext.addPluginModule(PluginModule.GUI);
        MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
        String childJava = getChildJava();
        enableMethod.setBody(enableMethod.getBody() +
                "GuiManager.getInstance().register(" + arg(0) + ", () -> {" +
                "try {" +
                BuildContext.declareLocalVariables() +
                "Inventory gui = Bukkit.createInventory(new GuiIdentifier(" + arg(0) + ")," + (type == InventoryType.CHEST ?
                    arg(2) + ",PluginMain.color(" + arg(1) + "));" :
                    InventoryType.class.getCanonicalName() + "." + type.name() + ",PluginMain.color(" + arg(1) + "));") +
                    childJava +
                    "return gui;" +
                "} catch (Exception e) { e.printStackTrace(); return null; }});");
    }

    @Override
    public void saveTo(DataConfig config) {
        super.saveTo(config);
        config.set("inv-type", type.name());
    }

    @Override
    public void loadFrom(DataConfig config) {
        try {
            type = config.contains("inv-type") ? InventoryType.valueOf(config.getString("inv-type")) : InventoryType.CHEST;
        } catch (IllegalArgumentException e) {
            type = InventoryType.CHEST;
        }
        typeText.setText(type.name());
        if (type == InventoryType.CHEST) {
            ExpressionParameter sizeParameter = new ExpressionParameter(int.class);
            getParameters().add(sizeParameter);
            getSyntaxLine(0).getChildren().addAll(new Text("with size"), sizeParameter);
        }
        super.loadFrom(config);
    }
}
