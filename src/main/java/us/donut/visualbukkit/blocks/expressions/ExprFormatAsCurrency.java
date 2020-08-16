package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Formats a number as currency", "Returns: string", "Requires: Vault"})
public class ExprFormatAsCurrency extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, "formatted as currency");
    }

    @Override
    public String toJava() {
        return "VaultHook.getEconomy().format(" + arg(0) + ")";
    }
}
