package com.yibo.security.core.social.qq.connect;

import com.yibo.security.core.social.qq.api.QQ;
import com.yibo.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 15:21
 * @Description:
 *
 * QQServiceProvider qq服务提供商，即通过这个类向qq发起请求
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    //将用户导向认证服务器的地址，即获取Authorization Code
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    //获取accessToken的地址，即通过Authorization Code获取Access Token
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String appSecret) {
        super(new QQOAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
