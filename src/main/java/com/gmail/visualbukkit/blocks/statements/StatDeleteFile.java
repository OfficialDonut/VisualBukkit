package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Category(Category.IO)
@Description("Deletes a file")
public class StatDeleteFile extends StatementBlock {

    public StatDeleteFile() {
        init("delete ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".delete();";
    }
}
