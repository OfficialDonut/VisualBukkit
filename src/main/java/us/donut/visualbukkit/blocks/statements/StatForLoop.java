package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.UUID;

@Description("Iterates a certain number of times")
@Category(StatementCategory.CONTROLS)
public class StatForLoop extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax("loop", int.class, "times");
    }

    @Override
    public String toJava() {
        String indexVar = "a" + UUID.randomUUID().toString().replace("-", "");
        return "for (int " + indexVar + "=0;" + indexVar + "<" + arg(0) + ";" + indexVar + "++) {" +
                getChildJava() +
                "}";
    }
}
