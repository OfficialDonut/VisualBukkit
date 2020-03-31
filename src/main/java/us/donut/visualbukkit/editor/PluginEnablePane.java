package us.donut.visualbukkit.editor;

import javafx.scene.control.Label;
import javassist.CtClass;
import javassist.CtMethod;

import java.util.StringJoiner;

public class PluginEnablePane extends BlockPane {

    public PluginEnablePane(Project project) {
        super(project, "Plugin Enable");
        Label label = new Label("Plugin Enable");
        label.getStyleClass().add("block-pane-label");
        getInfoArea().getChildren().addAll(label, new Label("(code placed here will run when the plugin enables)"));
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src = "Map tempVariables = new HashMap();" + stringJoiner.toString();
        enableMethod.insertAfter(src);
    }
}
