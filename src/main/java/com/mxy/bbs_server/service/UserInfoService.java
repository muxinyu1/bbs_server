package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.UserInfo;
import com.mxy.bbs_server.entity.UserInfoData;
import com.mxy.bbs_server.entity.UserInfoRequest;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.response.userinfo.UserInfoResponse;
import com.mxy.bbs_server.response.userinfo.UserInfoResponseFailedReason;
import com.mxy.bbs_server.utility.Utility;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class UserInfoService {
    private final UserInfoMapper userInfoMapper;

    public UserInfoService(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public UserInfoResponse update(UserInfoRequest userInfoRequest) throws IOException {
        final var userInfoToQuery = new UserInfoData(userInfoRequest.getUsername(), null, null, null, null, null);
        if (userInfoMapper.query(userInfoToQuery) == null) {
            return new UserInfoResponse(false, UserInfoResponseFailedReason.USERNAME_DOES_NOT_EXIST, null);
        }
        final var avatar = Utility.saveAvatar(userInfoRequest.getAvatar(), userInfoRequest.getUsername());
        final var previousUserInfo = userInfoMapper.query(userInfoToQuery);
        userInfoMapper.update(new UserInfoData(userInfoRequest.getUsername(), userInfoRequest.getNickname(), userInfoRequest.getPersonalSign(), avatar, previousUserInfo.getMyPosts(), previousUserInfo.getMyCollections()));
        final var userInfoRes = userInfoMapper.query(userInfoToQuery);
        return new UserInfoResponse(true, null, new UserInfo(userInfoRes.getUsername(),
                userInfoRes.getNickname(),
                userInfoRes.getPersonalSign(),
                userInfoRes.getAvatarUrl(),
                Utility.fromJson(userInfoRes.getMyPosts(), ArrayList.class),
                Utility.fromJson(userInfoRes.getMyCollections(), ArrayList.class)
        ));
    }

    public UserInfoResponse query(UserInfoRequest userInfoRequest) {
        var userInfoRes = userInfoMapper.query(new UserInfoData(userInfoRequest.getUsername(), null, null, null, null, null));
        if (userInfoRes == null) {
            return new UserInfoResponse(false, UserInfoResponseFailedReason.USERNAME_DOES_NOT_EXIST, null);
        }
        return new UserInfoResponse(true, null,
                new UserInfo(userInfoRes.getUsername(),
                        userInfoRes.getNickname(),
                        userInfoRes.getPersonalSign(),
                        userInfoRes.getAvatarUrl(),
                        Utility.fromJson(userInfoRes.getMyPosts(), ArrayList.class),
                        Utility.fromJson(userInfoRes.getMyCollections(), ArrayList.class)
                )
        );
    }
}
