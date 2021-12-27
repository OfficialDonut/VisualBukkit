package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.io.File;

public class StatWriteToFile extends Statement {

    public StatWriteToFile() {
        super("stat-write-to-file", "Write To File", "File", "Writes a string to a file");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("File", ClassInfo.of(File.class)), new ExpressionParameter("String", ClassInfo.STRING), new ChoiceParameter("Mode", "WRITE", "APPEND")) {
            @Override
            public String toJava() {
                return "java.nio.file.Files.write(" + arg(0) + ".toPath()," +
                        "Collections.singleton(" + arg(1) + ")," +
                        "java.nio.charset.StandardCharsets.UTF_8," +
                        "java.nio.file.StandardOpenOption." + arg(2) + ");";
            }
        };
    }
}
