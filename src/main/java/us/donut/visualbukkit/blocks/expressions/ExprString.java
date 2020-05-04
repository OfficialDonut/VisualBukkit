package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.InputParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Category("String")
@Description({"A custom string", "Returns: string"})
public class ExprString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(new InputParameter());
    }

    @Override
    public String toJava() {
        String encodedString = Base64.getEncoder().encodeToString(arg(0).getBytes(StandardCharsets.UTF_8));
        return "PluginMain.decode(\"" + encodedString + "\")";
    }
}
