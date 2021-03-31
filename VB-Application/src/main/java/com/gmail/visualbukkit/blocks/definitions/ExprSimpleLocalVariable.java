package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Pattern;

public class ExprSimpleLocalVariable extends Expression {

    protected static final Pattern VAR_PATTERN = Pattern.compile("\\$[a-z0-9]{32}");

    public ExprSimpleLocalVariable() {
        super("expr-simple-local-variable", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.getStyleClass().add("simple-local-variable");

        return new Block(this, inputParameter) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.declareLocalVariable(getVariable(arg(0)));
            }

            @Override
            public String toJava() {
                return getVariable(arg(0));
            }
        };
    }

    @SuppressWarnings("UnstableApiUsage")
    protected static String getVariable(String string) {
        return "$" + Hashing.murmur3_128().hashString(string, StandardCharsets.UTF_8).toString();
    }

    protected static String getRandomVariable() {
        return getVariable(UUID.randomUUID().toString());
    }
}
