package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.ReviewRequest;
import com.mxy.bbs_server.response.review.ReviewResponse;
import com.mxy.bbs_server.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ReviewResponse add(ReviewRequest reviewRequest) throws IOException {
        return reviewService.add(reviewRequest);
    }

    @PostMapping("/query")
    public ReviewResponse query(ReviewRequest reviewRequest) {
        return reviewService.query(reviewRequest);
    }

    @GetMapping("query/{reviewId}")
    public ReviewResponse query(@PathVariable("reviewId") String reviewId) {
        return reviewService.query(new ReviewRequest(reviewId, "", "", "", null));
    }
}
