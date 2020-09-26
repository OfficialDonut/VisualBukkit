package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

import java.util.List;

@Description({"A variable", "Returns: object"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprVariable extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax(new BinaryChoiceParameter("non-persistent", "persistent"), "variable", List.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.VARIABLES);
        if (arg(0).equals("persistent")) {
            BuildContext.addPluginModule(PluginModule.PERSISTENT_VARIABLES);
        }
        return "VariableManager.getValue(" + arg(0).equals("persistent") + "," + arg(1) + ")";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addPluginModule(PluginModule.VARIABLES);
        if (arg(0).equals("persistent")) {
            BuildContext.addPluginModule(PluginModule.PERSISTENT_VARIABLES);
        }
        switch (modificationType) {
            case SET: return "VariableManager.setValue(" + arg(0).equals("persistent") + "," + delta + "," + arg(1) + ");";
            case ADD: return "VariableManager.addToValue(" + arg(0).equals("persistent") + "," + delta + "," + arg(1) + ");";
            case REMOVE: return "VariableManager.removeFromValue(" + arg(0).equals("persistent") + "," + delta + "," + arg(1) + ");";
            case CLEAR: return modify(ModificationType.SET, "null");
            default: return null;
        }
    }
}
