package com.gmail.visualbukkit.blocks;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BlockDefinition {

    String uid();
    String name();
}
