package com.javaclimb.cate.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

/**
 * 注册验证码服务接口。
 */
public interface RegistrationCaptchaService {
    /**
     * 生成与会话绑定的验证码，返回图片、标识和有效期，不返回答案。
     */
    JSONObject create(HttpSession session);

    /**
     * 校验并销毁当前验证码；缺失、错误或过期时返回 false。
     */
    boolean consume(HttpSession session, String id, String input);
}
