package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

import java.io.File;

@Category(Category.IO)
@Description("Writes a string to a file")
public class StatWriteToFile extends StatementBlock {

    public StatWriteToFile() {
        init(new BinaryChoiceParameter("write", "append"), " ", String.class, " to ", File.class);
    }

    @Override
    public String toJava() {
        return "Files.write(" + arg(2) + ".toPath()," +
                "Collections.singleton(" + arg(1) + ")," +
                "java.nio.charset.StandardCharsets.UTF_8," +
                "java.nio.file.StandardOpenOption." + arg(0).toUpperCase() + ");";
    }
}
