package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.PostRequest;
import com.mxy.bbs_server.response.post.PostResponse;
import com.mxy.bbs_server.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public PostResponse add(PostRequest postRequest) throws IOException {
        return postService.add(postRequest);
    }

    @PostMapping("/query")
    public PostResponse query(PostRequest postRequest) {
        return postService.query(postRequest);
    }
}
