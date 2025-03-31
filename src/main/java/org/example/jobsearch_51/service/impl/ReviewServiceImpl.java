package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dao.ReviewDao;
import org.example.jobsearch_51.dto.ReviewDto;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.AccessDeniedException;
import org.example.jobsearch_51.exceptions.ReviewNotFoundException;
import org.example.jobsearch_51.model.Review;
import org.example.jobsearch_51.service.ReviewService;
import org.example.jobsearch_51.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDao reviewDao;
    private final UserService userService;

    @Override
    public List<ReviewDto> getReviewsByVacancy(int vacancyId) {
        log.info("Getting reviews for vacancy with ID: {}", vacancyId);
        return reviewDao.getReviewsByVacancyId(vacancyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addReview(ReviewDto reviewDto, Authentication authentication) {
        String email = authentication.getName();
        log.info("User with email {} is adding a review for vacancy ID: {}", email, reviewDto.getVacancyId());

        UserDto user = userService.getUserByEmail(email);
        reviewDto.setUserId(user.getId());

        Review review = convertToEntity(reviewDto);
        reviewDao.createReview(review);
        log.info("Review added successfully for vacancy ID: {} by user ID: {}", reviewDto.getVacancyId(), user.getId());
    }

    @Override
    public void deleteReview(int id, UserDto currentUser) {
        log.info("Attempting to delete review with ID: {} by user ID: {}", id, currentUser.getId());

        Review review = reviewDao.getReviewById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        if (review.getUserId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Access denied: User ID {} attempted to delete review ID {} created by user ID {}",
                    currentUser.getId(), id, review.getUserId());
            throw new AccessDeniedException("Только автор отзыва или администратор может удалить отзыв");
        }

        reviewDao.deleteReview(id);
        log.info("Review with ID: {} deleted successfully by user ID: {}", id, currentUser.getId());
    }

    private ReviewDto convertToDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .vacancyId(review.getVacancyId())
                .userId(review.getUserId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdDate(review.getCreatedDate())
                .build();
    }

    private Review convertToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setVacancyId(reviewDto.getVacancyId());
        review.setUserId(reviewDto.getUserId());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());
        return review;
    }
}