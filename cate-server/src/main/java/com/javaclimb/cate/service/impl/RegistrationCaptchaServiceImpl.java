package com.javaclimb.cate.service.impl;

import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.service.RegistrationCaptchaService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.UUID;

@Service
public class RegistrationCaptchaServiceImpl implements RegistrationCaptchaService {
    private static final String SESSION_KEY = RegistrationCaptchaService.class.getName();
    private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 5;
    private static final long TTL_MILLIS = 5 * 60 * 1000L;
    private final Clock clock;
    private final SecureRandom random;

    public RegistrationCaptchaServiceImpl() {
        this(Clock.systemUTC(), new SecureRandom());
    }

    RegistrationCaptchaServiceImpl(Clock clock, SecureRandom random) {
        this.clock = clock;
        this.random = random;
    }

    @Override
    public JSONObject create(HttpSession session) {
        ShearCaptcha captcha = new ShearCaptcha(150, 44, CODE_LENGTH, 4);
        captcha.setGenerator(new CodeGenerator() {
            @Override
            public String generate() {
                StringBuilder code = new StringBuilder(CODE_LENGTH);
                for (int i = 0; i < CODE_LENGTH; i++) {
                    code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
                }
                return code.toString();
            }

            @Override
            public boolean verify(String code, String input) {
                return code.equalsIgnoreCase(input);
            }
        });
        String image = captcha.getImageBase64Data();
        String id = UUID.randomUUID().toString();
        synchronized (session) {
            session.setAttribute(SESSION_KEY, new Challenge(id, captcha.getCode(), clock.millis() + TTL_MILLIS));
        }
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("captchaId", id);
        result.put("image", image);
        result.put("expiresIn", TTL_MILLIS / 1000);
        return result;
    }

    // 每次提交都销毁验证码，防止重放和反复猜测；答案始终只保存在服务端。
    @Override
    public boolean consume(HttpSession session, String id, String input) {
        if (session == null) {
            return false;
        }
        synchronized (session) {
            Challenge challenge = (Challenge) session.getAttribute(SESSION_KEY);
            session.removeAttribute(SESSION_KEY);
            return challenge != null && id != null && input != null
                    && challenge.id.equals(id) && clock.millis() < challenge.expiresAt
                    && challenge.code.equalsIgnoreCase(input.trim());
        }
    }

    private static final class Challenge implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String id;
        private final String code;
        private final long expiresAt;

        private Challenge(String id, String code, long expiresAt) {
            this.id = id;
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }
}
