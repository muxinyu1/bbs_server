package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReviewMapper {

    @Insert("insert into Review (id, date, username, content, images, likeNum) values (#{id}, #{date}, #{username}, #{content}, #{images}, #{likeNum})")
    void add(Review review);

    @Select("select * from Review where id = #{id}")
    Review query(Review review);

    @Update("update Review set date = #{date}, username = #{username}, content = #{content}, images = #{images}, likeNum = #{likeNum} where id = #{id}")
    void update(Review review);
}
