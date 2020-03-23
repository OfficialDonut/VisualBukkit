package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.Sound;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Plays a sound at a location")
public class StatPlaySoundAtLocation extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("play", Sound.class, "at", Location.class, "with volume", float.class, "and pitch", float.class);
    }

    @Override
    public String toJava() {
        String locVar = randomVar();
        return "Location " + locVar + "=" + arg(1) + ";" +
                locVar + ".getWorld().playSound(" + locVar + "," + arg(0) + "," + arg(2) + "," + arg(3) + ");";
    }
}
