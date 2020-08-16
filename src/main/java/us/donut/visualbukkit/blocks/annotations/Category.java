package us.donut.visualbukkit.blocks.annotations;

import us.donut.visualbukkit.blocks.StatementCategory;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Category {

    StatementCategory[] value();
}
