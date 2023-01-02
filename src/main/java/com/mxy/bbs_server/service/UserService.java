package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.entity.UserInfoData;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.mapper.UserMapper;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.response.user.UserResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.NginxHelper;
import com.mxy.bbs_server.utility.Utility;
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
        userInfoMapper.add(new UserInfoData(
                user.getUsername(),
                Const.DEFAULT_NICKNAME,
                Const.DEFAULT_PERSONAL_SIGN,
                NginxHelper.getAbsoluteUrl(Const.DEFAULT_AVATAR_URL),
                Utility.toJson(Const.DEFAULT_POSTS),
                Utility.toJson(Const.DEFAULT_COLLECTIONS))
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
