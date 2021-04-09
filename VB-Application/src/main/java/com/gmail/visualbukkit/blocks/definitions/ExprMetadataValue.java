package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class ExprMetadataValue extends Expression {

    public ExprMetadataValue() {
        super("expr-metadata-value", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.metadata.Metadatable")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(METADATA_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.getMetadataValue(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }

    private static final String METADATA_METHOD =
            "public static Object getMetadataValue(org.bukkit.metadata.Metadatable metadatable, String key) {\n" +
            "    for (org.bukkit.metadata.MetadataValue value : metadatable.getMetadata(key)) {\n" +
            "        if (PluginMain.getInstance().equals(value.getOwningPlugin())) {\n" +
            "            return value.value();\n" +
            "        }\n" +
            "    }\n" +
            "    return null;\n" +
            "}";
}
