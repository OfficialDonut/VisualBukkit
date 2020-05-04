package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Formats a number as currency", "Returns: string", "Requires: Vault"})
public class ExprFormatCurrency extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(double.class, "formatted as currency");
    }

    @Override
    public String toJava() {
        return "vaultEconomy.format(" + arg(0) + ")";
    }
}
