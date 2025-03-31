package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.ReviewDto;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.service.ReviewService;
import org.example.jobsearch_51.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {
    private final ReviewService reviewService;
    private final SecurityUtil securityUtil;

    @GetMapping("vacancy/{vacancyId}")
    public List<ReviewDto> getReviewsByVacancy(
            @PathVariable @Min(value = 1, message = "ID вакансии должен быть положительным числом") int vacancyId) {
        log.info("Requesting reviews for vacancy id: {}", vacancyId);
        return reviewService.getReviewsByVacancy(vacancyId);
    }

    @PostMapping
    public HttpStatus addReview(@Valid @RequestBody ReviewDto reviewDto, Authentication authentication) {
        log.info("Adding review from user: {}", authentication.getName());
        reviewService.addReview(reviewDto, authentication);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteReview(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        if (!securityUtil.isAuthenticated()) {
            log.warn("Unauthorized attempt to delete review with ID: {}", id);
            return HttpStatus.UNAUTHORIZED;
        }

        UserDto currentUser = securityUtil.getCurrentUser();
        log.info("Deleting review with ID: {} by user: {}", id, currentUser.getEmail());
        reviewService.deleteReview(id, currentUser);
        return HttpStatus.OK;
    }
}