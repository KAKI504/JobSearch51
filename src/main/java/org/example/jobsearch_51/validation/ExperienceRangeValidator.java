package org.example.jobsearch_51.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.jobsearch_51.dto.WorkExperienceDto;

public class ExperienceRangeValidator implements ConstraintValidator<ValidExperienceRange, WorkExperienceDto> {
    @Override
    public void initialize(ValidExperienceRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(WorkExperienceDto experience, ConstraintValidatorContext context) {
        return experience.getExpTo() >= experience.getExpFrom();
    }
}