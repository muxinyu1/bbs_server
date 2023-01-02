package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {

    @Insert("insert into UserInfo (username, nickname, personalSign, avatarUrl, myPosts, myCollections) values (#{username}, #{nickname}, #{personalSign}, #{avatarUrl}, #{myPosts}, #{myCollections})")
    void add(UserInfo userInfo);

    @Update("update UserInfo set nickname = #{nickname}, personalSign = #{personalSign}, avatarUrl = #{avatarUrl}, myPosts = #{myPosts}, myCollections = #{myCollections} where username = #{username}")
    void update(UserInfo userInfo);

    @Select("select * from UserInfo where username = #{username}")
    UserInfo query(UserInfo userInfo);
}
