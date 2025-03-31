package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.ReviewDto;
import org.example.jobsearch_51.dto.UserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getReviewsByVacancy(int vacancyId);
    void addReview(ReviewDto reviewDto, Authentication authentication);
    void deleteReview(int id, UserDto currentUser);
}