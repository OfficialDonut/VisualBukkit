package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.TypeHandler;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;

import java.util.Set;
import java.util.TreeSet;

@Description("Checks if an object is a certain type")
public class ExprIsType extends ExpressionBlock<Boolean> {

    private static Set<String> types = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    static {
        types.addAll(TypeHandler.getAliases());
    }

    public ExprIsType() {
        init(Object.class, " is of type ", new ChoiceParameter(types));
    }

    @Override
    public String toJava() {
        String clazz = TypeHandler.getType(arg(1)).getCanonicalName() + ".class";
        return "(" + arg(0) + " != null && " + clazz + ".isAssignableFrom(" + arg(0) + ".getClass()))";
    }
}