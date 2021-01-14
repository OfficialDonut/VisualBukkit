package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructProcedure;

@Description("The arguments of a procedure")
@SuppressWarnings("rawtypes")
public class ExprProcedureArguments extends ExpressionBlock<List> {

    public ExprProcedureArguments() {
        init("arguments");
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Procedure arguments must be used in a procedure", StructProcedure.class);
    }

    @Override
    public String toJava() {
        return "procedureArgs";
    }
}