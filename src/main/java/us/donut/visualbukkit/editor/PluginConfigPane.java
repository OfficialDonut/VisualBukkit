package us.donut.visualbukkit.editor;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bukkit.configuration.ConfigurationSection;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import us.donut.visualbukkit.util.Loadable;
import us.donut.visualbukkit.util.TitleLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginConfigPane extends Tab implements Loadable {

    private Project project;
    private CodeArea textArea = new CodeArea();
    private Label projectStructureLabel;

    public PluginConfigPane(Project project) {
        super("Plugin Config");
        this.project = project;
        projectStructureLabel = new Label(getText());
        projectStructureLabel.setOnMouseEntered(e -> projectStructureLabel.setStyle("-fx-text-fill: gold;"));
        projectStructureLabel.setOnMouseExited(e -> projectStructureLabel.setStyle(null));
        projectStructureLabel.setOnMouseClicked(e -> open());
        HBox infoArea = new HBox(15);
        infoArea.getStyleClass().add("block-pane-info-area");
        infoArea.getChildren().addAll(
                new TitleLabel("Plugin Config", 2),
                new Label("(default config.yml)"));
        VBox content = new VBox(infoArea, textArea);
        setContent(content);
        textArea.prefHeightProperty().bind(content.heightProperty());
        textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));
        Pattern whiteSpace = Pattern.compile("^\\s+");
        textArea.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                int currentParagraph = textArea.getCurrentParagraph();
                Matcher matcher = whiteSpace.matcher(textArea.getParagraph( currentParagraph - 1).getSegments().get(0));
                if (matcher.find()) {
                    textArea.insertText(textArea.getCaretPosition(), matcher.group());
                }
            }
        });
    }

    public void open() {
        if (!project.getTabPane().getTabs().contains(this)) {
            project.getTabPane().getTabs().add(this);
        }
    }

    @Override
    public void unload(ConfigurationSection section) {
        if (getTabPane() != null) {
            section.set("open", true);
        }
        section.set("content", textArea.getText());
    }

    @Override
    public void load(ConfigurationSection section) {
        if (section.getBoolean("open")) {
            open();
        }
        textArea.appendText(section.getString("content", ""));
    }

    public Label getProjectStructureLabel() {
        return projectStructureLabel;
    }

    public String getConfigContent() {
        return textArea.getText();
    }
}
