package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class StatRenameFile extends Statement {

    public StatRenameFile() {
        super("stat-rename-file", "Rename File", "File", "Renames/moves a file");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Source", ClassInfo.of(File.class)), new ExpressionParameter("Target", ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return "java.nio.file.Files.move(" + arg(0) + ".toPath()," + arg(1) + ".toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);";
            }
        };
    }
}
