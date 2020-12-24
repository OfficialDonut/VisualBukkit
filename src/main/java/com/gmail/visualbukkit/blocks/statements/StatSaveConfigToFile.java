package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@Category(Category.IO)
@Description("Saves a config to a file")
public class StatSaveConfigToFile extends StatementBlock {

    public StatSaveConfigToFile() {
        init("save ", FileConfiguration.class, " to ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".save(" + arg(1) + ");";
    }
}
