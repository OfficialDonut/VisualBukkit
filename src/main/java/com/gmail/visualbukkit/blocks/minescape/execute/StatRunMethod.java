package com.gmail.visualbukkit.blocks.minescape.execute;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;

@Category(Category.MINESCAPE)
@Description("Run Method")
public class StatRunMethod extends StatementBlock {

    public StatRunMethod() {
        init("Run Method ", new StringLiteralParameter());
    }

    @Override
    public String toJava() {
        return "";
    }
}
