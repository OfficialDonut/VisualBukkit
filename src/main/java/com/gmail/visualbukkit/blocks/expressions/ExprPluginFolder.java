package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("The plugin data folder")
public class ExprPluginFolder extends ExpressionBlock<File> {

    public ExprPluginFolder() {
        init("plugin folder");
    }

    @Override
    public String toJava() {
        return "PluginMain.getInstance().getDataFolder()";
    }
}
