package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.*;
import com.mxy.bbs_server.mapper.PostMapper;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.response.post.PostResponse;
import com.mxy.bbs_server.response.post.PostResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.Utility;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostMapper postMapper;

    private final UserInfoMapper userInfoMapper;

    public PostService(PostMapper postMapper, UserInfoMapper userInfoMapper) {
        this.postMapper = postMapper;
        this.userInfoMapper = userInfoMapper;
    }

    public PostResponse add(PostRequest postRequest) throws IOException {
        if (postMapper.query(new PostData(postRequest.getId(), null, null, null, null, null, null, null)) != null) {
            return new PostResponse(false, PostResponseFailedReason.POST_ALREADY_EXISTS, null);
        }
        final var images = Utility.savePostImages(postRequest.getImages(), postRequest.getId());
        postMapper.add(new PostData(postRequest.getId(),
                Utility.getDate(Const.DATE_FORMAT),
                postRequest.getOwner(),
                postRequest.getTitle(),
                postRequest.getContent(),
                Utility.toJson(images),
                0,
                Utility.toJson(new ArrayList<String>()))
        );
        final var previousUserInfo = userInfoMapper.query(new UserInfoData(postRequest.getOwner(), null, null, null, null, null));
        //在"我的帖子"中添加当前帖子
        final List<String> myPosts = Utility.fromJson(previousUserInfo.getMyPosts(), List.class);
        myPosts.add(postRequest.getId());
        previousUserInfo.setMyPosts(Utility.toJson(myPosts));
        userInfoMapper.update(previousUserInfo);
        final var postRes = postMapper.query(new PostData(postRequest.getId(), null, null, null, null, null, null, null));
        return new PostResponse(true, null, new Post(postRes.getId(),
                postRes.getDate(),
                postRes.getOwner(),
                postRes.getTitle(),
                postRes.getContent(),
                Utility.fromJson(postRes.getImages(), ArrayList.class),
                postRes.getLikeNum(), Utility.fromJson(postRes.getReviews(),
                ArrayList.class)
        ));
    }

    public PostResponse query(PostRequest postRequest) {
        var postRes = postMapper.query(new PostData(postRequest.getId(), null, null, null, null, null, null, null));
        if (postRes == null) {
            return new PostResponse(false, PostResponseFailedReason.POST_DOES_NOT_EXIST, null);
        }
        return new PostResponse(true, null,
                new Post(postRes.getId(),
                        postRes.getDate(),
                        postRes.getOwner(),
                        postRes.getTitle(),
                        postRes.getContent(),
                        Utility.fromJson(postRes.getImages(), ArrayList.class),
                        postRes.getLikeNum(), Utility.fromJson(postRes.getReviews(),
                        ArrayList.class)
                )
        );
    }
}
