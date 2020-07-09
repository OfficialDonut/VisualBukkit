package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Creates an explosion at a location")
public class StatCreateExplosion extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("create explosion")
                .line("location:", Location.class)
                .line("power:   ", float.class)
                .line("fire:    ", new ChoiceParameter("make fire", "do not make fire"))
                .line("blocks:  ", new ChoiceParameter("break blocks", "do not break blocks"));
    }

    @Override
    public String toJava() {
        return "createExplosion(" + arg(0) + "," + arg(1) + "," + arg(2).equals("make fire") + "," + arg(3).equals("break blocks") + ");";
    }

    @UtilMethod
    public static void createExplosion(Location loc, float power, boolean fire, boolean breakBlocks) {
        loc.getWorld().createExplosion(loc, power, fire, breakBlocks);
    }
}
