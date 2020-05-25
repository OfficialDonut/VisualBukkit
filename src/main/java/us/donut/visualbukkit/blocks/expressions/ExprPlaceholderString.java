package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.OfflinePlayer;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Replaces placeholders in a string", "Returns: string", "Requires: PlaceholderAPI"})
public class ExprPlaceholderString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "parsed with", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return "me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(" + arg(1) + "," + arg(0) + ")";
    }
}
