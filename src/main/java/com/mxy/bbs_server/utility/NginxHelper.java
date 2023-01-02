package com.mxy.bbs_server.utility;

public class NginxHelper {

    public static String getAbsoluteUrl(String absolutePath) {
        //TODO: 应该对absolutePath处理一下
        return Const.NGINX_PORT + absolutePath;
    }
}
