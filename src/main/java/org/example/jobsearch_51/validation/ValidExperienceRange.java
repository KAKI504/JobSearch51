package org.example.jobsearch_51.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExperienceRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExperienceRange {
    String message() default "Максимальный опыт должен быть больше или равен минимальному";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}