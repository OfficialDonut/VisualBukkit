package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The characters of a string", "Returns: list of strings"})
public class ExprStringCharacters extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("characters of", String.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".split(\"(?!^)\"))";
    }
}
