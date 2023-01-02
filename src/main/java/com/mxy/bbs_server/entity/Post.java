package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.yaml.snakeyaml.events.Event;

import java.util.List;


@Data
@AllArgsConstructor
public class Post {
    private String id;
    private String date;
    private String owner;
    private String title;
    private String content;
    private List<String> images;
    private Integer likeNum;
    private List<String> reviews;
}
