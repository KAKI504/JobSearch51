package org.example.jobsearch_51.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.jobsearch_51.dto.VacancyDto;

public class ExperienceRangeValidator implements ConstraintValidator<ValidExperienceRange, VacancyDto> {
    @Override
    public void initialize(ValidExperienceRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(VacancyDto vacancy, ConstraintValidatorContext context) {
        return vacancy.getExpTo() >= vacancy.getExpFrom();
    }
}