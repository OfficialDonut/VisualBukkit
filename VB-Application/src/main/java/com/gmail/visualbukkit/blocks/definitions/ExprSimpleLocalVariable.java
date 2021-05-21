package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class ExprSimpleLocalVariable extends SimpleExpression {

    protected static final Pattern VAR_PATTERN = Pattern.compile("\\$[a-z0-9]{32}");

    public ExprSimpleLocalVariable() {
        super("expr-simple-local-variable");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        InputParameter input = new InputParameter();
        input.getStyleClass().add("simple-local-variable-field");

        return new Block(this, input) {
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
}
