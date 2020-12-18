package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Category(Category.CONTROLS)
@Description("Runs code if the previous if statement failed and a condition is true")
public class StatElseIfStatement extends ParentBlock {

    private BinaryChoiceParameter booleanChoice = new BinaryChoiceParameter("true", "false");

    public StatElseIfStatement() {
        init("else if ", boolean.class, " is ", booleanChoice);
    }

    @Override
    public void update() {
        super.update();
        if (hasPrevious() && !(previous instanceof StatIfStatement) && !(previous instanceof StatElseIfStatement)) {
            setInvalid("'else if' must be placed directly after an 'if' or 'else if'");
        }
    }

    @Override
    public String toJava() {
        return booleanChoice.isFirst() ?
                "else if (" + arg(0) + ") {" + getChildJava() + "}" :
                "else if (!" + arg(0) + ") {" + getChildJava() + "}";
    }
}
