package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description({"Replaces placeholders in a string", "Returns: string", "Requires: PlaceholderAPI"})
public class ExprPlaceholderString extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, "parsed with", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.PlACEHOLDERAPI);
        return "me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(" + arg(1) + "," + arg(0) + ")";
    }
}
