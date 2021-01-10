package com.gmail.visualbukkit.blocks.annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Category {

    String[] value();
    
    String CONTROLS = "Controls";
    String ENTITY = "Entity";
    String EVENTS = "Events";
    String IO = "I/O";
    String ITEM = "Item";
    String PLAYER = "Player";
    String STATEMENTS = "All Blocks";
    String STRUCTURES = "Structures";
    String VARIABLES = "Variables";
    String WORLD = "World";
    String MINESCAPE = "MineScape";
}
