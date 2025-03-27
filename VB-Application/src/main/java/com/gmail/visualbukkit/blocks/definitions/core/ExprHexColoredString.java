package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-hex-colored-string", name = "Hex Colored String", description = "A string with '&' and hex color codes")
public class ExprHexColoredString extends ExpressionBlock {

    public ExprHexColoredString() {
        addParameter("String", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/ChatColor.html#translateAlternateColorCodes(char,java.lang.String)"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(String.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (!buildInfo.getMainClass().hasField("HEX_PATTERN")) {
            buildInfo.getMainClass().addField("public static final java.util.regex.Pattern HEX_PATTERN = java.util.regex.Pattern.compile(\"#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])\");");
        }
        return "ChatColor.translateAlternateColorCodes('&', PluginMain.HEX_PATTERN.matcher(" + arg(0, buildInfo) + ").replaceAll(\"&x&$1&$2&$3&$4&$5&$6\"))";
    }
}
