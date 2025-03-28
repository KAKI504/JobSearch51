package org.example.jobsearch_51.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.jobsearch_51.dto.EducationDto;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, EducationDto> {
    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(EducationDto education, ConstraintValidatorContext context) {
        if (education.getStartDate() == null || education.getEndDate() == null) {
            return true;
        }
        return education.getEndDate().isAfter(education.getStartDate()) ||
                education.getEndDate().isEqual(education.getStartDate());
    }
}