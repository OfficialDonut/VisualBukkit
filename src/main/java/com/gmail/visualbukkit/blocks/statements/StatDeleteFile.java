package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.io.File;

@Description("Deletes a file")
public class StatDeleteFile extends StatementBlock {

    public StatDeleteFile() {
        init("delete file ", File.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".delete();";
    }
}
