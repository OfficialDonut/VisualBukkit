package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;

@Category("Controls")
@Description("Runs code if a condition is true")
public class StatIfStatement extends ParentBlock {

    private ExpressionParameter condition = new ExpressionParameter(boolean.class);
    private BinaryChoiceParameter booleanChoice = new BinaryChoiceParameter("true", "false");

    public StatIfStatement() {
        init("if ", condition, " is ", booleanChoice);
    }

    @Override
    public String toJava() {
        return booleanChoice.isFirst() ?
                "if (" + condition + ") {" + getChildJava() + "}" :
                "if (!(" + condition + ")) {" + getChildJava() + "}";
    }
}
