package ru.javawebinar.topjava.util.formatter;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface CustomDateTimeFormat {
    String style() default "SS";
    Type type();

    public enum Type {
        DATE,
        TIME
    }
}
