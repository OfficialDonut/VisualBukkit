package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class StatDeleteFile extends Statement {

    public StatDeleteFile() {
        super("stat-delete-file", "Delete File", "File", "Deletes a file");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("File", ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return arg(0) + ".delete();";
            }
        };
    }
}
