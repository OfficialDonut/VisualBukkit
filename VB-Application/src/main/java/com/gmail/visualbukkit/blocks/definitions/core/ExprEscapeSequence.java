package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.HashMap;
import java.util.Map;

@BlockDefinition(id = "expr-escape-sequence", name = "Escape Sequence", description = "An escape sequence")
public class ExprEscapeSequence extends ExpressionBlock {

    private static final Map<String, String> escapeSequences = new HashMap<>();

    static {
        escapeSequences.put("Tab (\\t)", "\\t");
        escapeSequences.put("Backspace (\\b)", "\\b");
        escapeSequences.put("Newline (\\n)", "\\n");
        escapeSequences.put("Carriage Return (\\r)", "\\r");
        escapeSequences.put("Form Feed (\\f)", "\\f");
    }

    public ExprEscapeSequence() {
        addParameter("Sequence", new ChoiceParameter(escapeSequences.keySet()));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(char.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "'" + escapeSequences.get(arg(0, buildInfo)) + "'";
    }
}
