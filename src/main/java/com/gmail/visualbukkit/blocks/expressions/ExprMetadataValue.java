package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.metadata.Metadatable;

@Description("A metadata value")
public class ExprMetadataValue extends ExpressionBlock<Object> {

    public ExprMetadataValue() {
        init("value of metadata ", String.class, " for ", Metadatable.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(METADATA_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.getMetadataValue(" + arg(1) + "," + arg(0) + ")";
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
