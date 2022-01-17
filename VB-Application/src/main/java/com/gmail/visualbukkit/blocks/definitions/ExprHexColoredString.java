package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;

public class ExprHexColoredString extends Expression {

    public ExprHexColoredString() {
        super("expr-hex-colored-string", "Hex Colored String", "String", "Colors a string with '&' as the color code character along with hex colors");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("String", ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addPluginModule(HEX_MODULE);
            }

            @Override
            public String toJava() {
                return "ChatColor.translateAlternateColorCodes('&', PluginMain.HEX_PATTERN.matcher(" + arg(0) + ").replaceAll(\"&x&$1&$2&$3&$4&$5&$6\"))";
            }
        };
    }

    private static final PluginModule HEX_MODULE = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.getMainClass().addField("public static java.util.regex.Pattern HEX_PATTERN = java.util.regex.Pattern.compile(\"#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])\");");
        }
    };
}
