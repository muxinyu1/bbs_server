package com.mxy.bbs_server.response.userinfo;

import com.mxy.bbs_server.entity.UserInfo;
import com.mxy.bbs_server.entity.UserInfoData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Boolean success;
    private UserInfoResponseFailedReason userInfoResponseFailedReason;
    private UserInfoData userInfo;
}
