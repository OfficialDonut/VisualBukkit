package us.donut.visualbukkit.editor;

import javafx.scene.control.Label;
import javassist.CtClass;
import javassist.CtMethod;
import us.donut.visualbukkit.util.TitleLabel;

import java.util.StringJoiner;

public class PluginEnablePane extends BlockPane {

    public PluginEnablePane(Project project) {
        super(project, "Plugin Enable");
        getInfoArea().getChildren().addAll(
                new TitleLabel("Plugin Enable", 2),
                new Label("(code placed here will run when the plugin enables)"));
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        CtMethod enableMethod = mainClass.getDeclaredMethod("onEnable");
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src =
                "Object localVarScope = new Object();" +
                stringJoiner.toString();
        enableMethod.insertAfter(src);
    }
}
