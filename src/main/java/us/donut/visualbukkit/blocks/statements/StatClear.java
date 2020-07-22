package us.donut.visualbukkit.blocks.statements;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

public class StatClear extends ModifierBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode();
    }

    @Override
    public void init(ExpressionBlockInfo<?> expressionBlockInfo) {
        super.init(expressionBlockInfo);
        ExpressionParameter expressionParameter = new ExpressionParameter(Object.class);
        expressionParameter.setExpression(expressionBlock);
        getSyntaxNode().add("clear", expressionParameter);
    }

    @Override
    public String toJava() {
        return expressionBlock.modify(ModificationType.CLEAR, null);
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        BlockInfo<?> expressionBlockInfo = BlockRegistry.getInfo(section.getString("parameters.0.block-type"));
        if (expressionBlockInfo instanceof ExpressionBlockInfo) {
            init((ExpressionBlockInfo<?>) expressionBlockInfo);
            expressionBlock.load(section.getConfigurationSection("parameters.0"));
        } else {
            throw new IllegalStateException();
        }
    }
}
