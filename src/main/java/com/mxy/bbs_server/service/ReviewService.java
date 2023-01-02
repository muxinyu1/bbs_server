package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.Post;
import com.mxy.bbs_server.entity.Review;
import com.mxy.bbs_server.entity.ReviewRequest;
import com.mxy.bbs_server.mapper.PostMapper;
import com.mxy.bbs_server.mapper.ReviewMapper;
import com.mxy.bbs_server.response.review.ReviewResponse;
import com.mxy.bbs_server.response.review.ReviewResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.Utility;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReviewService {
    private final ReviewMapper reviewMapper;

    private final PostMapper postMapper;

    public ReviewService(ReviewMapper reviewMapper, PostMapper postMapper) {
        this.reviewMapper = reviewMapper;
        this.postMapper = postMapper;
    }

    public ReviewResponse add(ReviewRequest reviewRequest) throws IOException {
        final var reviewToQuery = new Review(reviewRequest.getId(), null, null, null, null,null, null);
        if (reviewMapper.query(reviewToQuery) != null) {
            return new ReviewResponse(false, ReviewResponseFailedReason.REVIEW_ALREADY_EXISTS, null);
        }
        final var images = Utility.saveReviewImages(reviewRequest.getImages(), reviewRequest.getId());
        reviewMapper.add(new Review(reviewRequest.getId(), reviewRequest.getTargetPost(), Utility.getDate(Const.DATE_FORMAT), reviewRequest.getUsername(), reviewRequest.getContent(), images, 0));
        final var previousPost = postMapper.query(new Post(reviewRequest.getTargetPost(), null, null, null, null, null, null, null));
        //更新针对该post的评论
        previousPost.getReviews().add(reviewRequest.getId());
        postMapper.update(previousPost);
        return new ReviewResponse(true, null, reviewMapper.query(reviewToQuery));
    }

    public ReviewResponse query(ReviewRequest reviewRequest) {
        final var review = reviewMapper.query(new Review(reviewRequest.getId(), null, null, null, null, null, null));
        if (review == null) {
            return new ReviewResponse(false, ReviewResponseFailedReason.REVIEW_DOES_NOT_EXISTS, null);
        }
        return new ReviewResponse(true, null, review);
    }
}
