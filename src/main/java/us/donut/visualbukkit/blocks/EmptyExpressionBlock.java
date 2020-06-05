package us.donut.visualbukkit.blocks;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.UUID;

public class EmptyExpressionBlock<T> extends ChangeableExpressionBlock<T> {

    private Class<T> returnType;

    public EmptyExpressionBlock(Class<T> returnType) {
        this.returnType = returnType;
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode();
    }

    @Override
    public String toJava() {
        return "((" + returnType.getCanonicalName() + ") null)";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return getReturnType().getCanonicalName() + " a" + UUID.randomUUID().toString().replace("-", "") + "=" + delta + ";";
    }

    @Override
    public Class<T> getReturnType() {
        return returnType;
    }

    @Override
    public void unload(ConfigurationSection section) {}

    @Override
    public void load(ConfigurationSection section) {}
}
