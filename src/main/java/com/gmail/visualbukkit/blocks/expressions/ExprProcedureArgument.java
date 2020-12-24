package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructProcedure;

@Description("An argument of a procedure")
public class ExprProcedureArgument extends ExpressionBlock<Object> {

    public ExprProcedureArgument() {
        init("argument ", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Procedure argument must be used in a procedure", StructProcedure.class);
    }

    @Override
    public String toJava() {
        return "procedureArgs.get(" + arg(0) + ")";
    }
}