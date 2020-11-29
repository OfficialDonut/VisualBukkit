package com.gmail.visualbukkit.blocks.annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Category {

    String[] value();

    String EVENTS = "Events";
    String STATEMENTS = "All Statements";
    String STRUCTURES = "Structures";
}
