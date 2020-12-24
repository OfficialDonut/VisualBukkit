package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Category(Category.IO)
@Description("Creates a file")
public class StatCreateFile extends StatementBlock {

    public StatCreateFile() {
        init("create ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".createNewFile();";
    }
}
