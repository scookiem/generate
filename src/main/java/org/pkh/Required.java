package org.pkh;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Required {
    /*分组*/
    int group() default -1;

    /*组里个数*/
    int least() default 0;

    /*错误信息*/
    String message();
}
