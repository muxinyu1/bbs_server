package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.entity.UserInfo;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.mapper.UserMapper;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.response.user.UserResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserInfoMapper userInfoMapper;

    public UserService(UserMapper userMapper, UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
    }

    public UserResponse add(User user) {
        if (userMapper.query(user) != null) {
            //username已经存在
            return new UserResponse(false, UserResponseFailedReason.USERNAME_ALREADY_EXIST, user);
        }
        userMapper.add(user);
        userInfoMapper.add(new UserInfo(
                user.getUsername(),
                Const.DEFAULT_NICKNAME,
                Const.DEFAULT_PERSONAL_SIGN,
                Const.DEFAULT_AVATAR_URL,
                Const.DEFAULT_POSTS,
                Const.DEFAULT_COLLECTIONS)
        );
        return new UserResponse(true, null, user);
    }

    public UserResponse query(User user) {
        var userRes = userMapper.query(user);
        if (userRes == null) {
            return new UserResponse(false, UserResponseFailedReason.USERNAME_DOES_NOT_EXIST, user);
        }
        return new UserResponse(true, null, userRes);
    }
}
