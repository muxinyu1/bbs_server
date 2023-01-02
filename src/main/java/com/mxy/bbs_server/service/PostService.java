package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.Post;
import com.mxy.bbs_server.entity.PostRequest;
import com.mxy.bbs_server.entity.UserInfo;
import com.mxy.bbs_server.mapper.PostMapper;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.response.post.PostResponse;
import com.mxy.bbs_server.response.post.PostResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.Utility;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class PostService {
    private final PostMapper postMapper;

    private final UserInfoMapper userInfoMapper;

    public PostService(PostMapper postMapper, UserInfoMapper userInfoMapper) {
        this.postMapper = postMapper;
        this.userInfoMapper = userInfoMapper;
    }

    public PostResponse add(PostRequest postRequest) throws IOException {
        if (postMapper.query(new Post(postRequest.getId(), null, null, null, null, null, null, null)) != null) {
            return new PostResponse(false, PostResponseFailedReason.POST_ALREADY_EXISTS, null);
        }
        final var images = Utility.savePostImages(postRequest.getImages(), postRequest.getId());
        postMapper.add(new Post(postRequest.getId(),
                Utility.getDate(Const.DATE_FORMAT),
                postRequest.getOwner(),
                postRequest.getTitle(),
                postRequest.getContent(),
                images,
                0,
                new ArrayList<>())
        );
        final var previousUserInfo = userInfoMapper.query(new UserInfo(postRequest.getOwner(), null, null, null, null, null));
        //在"我的帖子"中添加当前帖子
        previousUserInfo.getMyPosts().add(postRequest.getId());
        userInfoMapper.update(previousUserInfo);
        final var postRes = postMapper.query(new Post(postRequest.getId(), null, null, null, null, null, null, null));
        return new PostResponse(true, null, postRes);
    }

    public PostResponse query(PostRequest postRequest) {
        var postRes = postMapper.query(new Post(postRequest.getId(), null, null, null, null, null, null, null));
        if (postRes == null) {
            return new PostResponse(false, PostResponseFailedReason.POST_DOES_NOT_EXIST, null);
        }
        return new PostResponse(true, null, postRes);
    }
}
