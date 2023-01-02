package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.ActionRequest;
import com.mxy.bbs_server.entity.Post;
import com.mxy.bbs_server.entity.Review;
import com.mxy.bbs_server.mapper.PostMapper;
import com.mxy.bbs_server.mapper.ReviewMapper;
import com.mxy.bbs_server.response.action.ActionResponse;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    private final PostMapper postMapper;

    private final ReviewMapper reviewMapper;

    public ActionService(PostMapper postMapper, ReviewMapper reviewMapper) {
        this.postMapper = postMapper;
        this.reviewMapper = reviewMapper;
    }

    public ActionResponse like(ActionRequest actionRequest) {
        if (actionRequest.getIsTargetPost()) {
            return likePost(actionRequest.getPostId());
        }
        return likeReview(actionRequest.getReviewId());
    }

    private ActionResponse likePost(String postId) {
        final var previousPost = postMapper.query(new Post(postId, null, null, null, null, null, null, null));
        previousPost.setLikeNum(previousPost.getLikeNum() + 1);
        postMapper.update(previousPost);
        return new ActionResponse(true);
    }

    private ActionResponse likeReview(String reviewId) {
        final var previousReview = reviewMapper.query(new Review(reviewId, null, null, null, null, null, null));
        previousReview.setLikeNum(previousReview.getLikeNum() + 1);
        reviewMapper.update(previousReview);
        return new ActionResponse(true);
    }
}
