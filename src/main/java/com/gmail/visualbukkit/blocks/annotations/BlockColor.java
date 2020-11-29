package com.gmail.visualbukkit.blocks.annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BlockColor {

    String value();
}
