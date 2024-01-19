package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.List;

@BlockDefinition(id = "expr-procedure-arguments", name = "Procedure Arguments", description = "The list of arguments passed to a procedure (must be used in a 'Procedure' plugin component)")
public class ExprProcedureArguments extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        if (!(getPluginComponentBlock() instanceof CompProcedure)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(List.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "args";
    }
}
