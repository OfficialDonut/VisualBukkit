package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Category(Category.IO)
@Description("Creates a directory")
public class StatCreateDirectory extends StatementBlock {

    public StatCreateDirectory() {
        init("create directory ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".mkdirs();";
    }
}
