package us.donut.visualbukkit.blocks.annotations;

import us.donut.visualbukkit.blocks.ModificationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Modifier {

    ModificationType[] value();
}
