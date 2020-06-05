package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

import java.util.concurrent.ThreadLocalRandom;

@Description({"Gets a random element from a list", "Returns: object"})
public class ExprRandomElement extends ExpressionBlock<Object> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("random element of", SimpleList.class);
    }

    @Override
    public String toJava() {
        return "getRandomElement(" + arg(0) + ")";
    }

    @UtilMethod
    public static Object getRandomElement(SimpleList list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
