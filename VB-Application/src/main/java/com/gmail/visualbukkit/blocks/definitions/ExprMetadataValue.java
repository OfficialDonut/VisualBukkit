package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprMetadataValue extends Expression {

    public ExprMetadataValue() {
        super("expr-metadata-value", "Metadata Value", "Bukkit", "A metadata value");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Target", ClassInfo.of("org.bukkit.metadata.Metadatable")), new ExpressionParameter("Key", ClassInfo.STRING)) {
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
            """
            public static Object getMetadataValue(org.bukkit.metadata.Metadatable metadatable, String key) {
                for (org.bukkit.metadata.MetadataValue value : metadatable.getMetadata(key)) {
                    if (PluginMain.getInstance().equals(value.getOwningPlugin())) {
                        return value.value();
                    }
                }
                return null;
            }
            """;
}
