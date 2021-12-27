package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class StatCreateDirectory extends Statement {

    public StatCreateDirectory() {
        super("stat-create-directory", "Create Directory", "File", "Creates a directory");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Directory", ClassInfo.of(File.class))) {
            @Override
            public String toJava() {
                return arg(0) + ".mkdirs();";
            }
        };
    }
}
