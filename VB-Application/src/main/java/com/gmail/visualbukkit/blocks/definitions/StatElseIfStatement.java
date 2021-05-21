package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.ui.LanguageManager;

public class StatElseIfStatement extends Container {

    public StatElseIfStatement() {
        super("stat-else-if-statement");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.BOOLEAN), new ChoiceParameter("normal", "negate condition")) {
            @Override
            public void update() {
                super.update();
                if (checkForPrevious("stat-if-statement") || checkForPrevious("stat-else-if-statement")) {
                    setValid();
                } else {
                    setInvalid(LanguageManager.get("error.invalid_else"));
                }
            }

            @Override
            public String toJava() {
                return arg(1).equals("normal") ?
                        ("else if (" + arg(0) + ") {" + getChildJava() + "}") :
                        ("else if (!" + arg(0) + ") {" + getChildJava() + "}");
            }
        };
    }
}
