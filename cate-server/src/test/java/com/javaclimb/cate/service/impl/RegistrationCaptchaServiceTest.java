package com.javaclimb.cate.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.cate.service.RegistrationCaptchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Base64;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class RegistrationCaptchaServiceTest {
    private Clock clock;
    private RegistrationCaptchaService service;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        clock = mock(Clock.class);
        when(clock.millis()).thenReturn(1000L);
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextInt(anyInt())).thenReturn(0, 8, 9, 10, 11);
        service = new RegistrationCaptchaServiceImpl(clock, random);
        session = new MockHttpSession();
    }

    @Test
    void generatesPngWithoutReturningTheAnswer() throws Exception {
        JSONObject challenge = service.create(session);
        assertEquals(4, challenge.size());
        assertEquals(1, challenge.getIntValue("code"));
        assertEquals(300, challenge.getIntValue("expiresIn"));
        String image = challenge.getString("image");
        assertTrue(image.startsWith("data:image/png;base64,"));
        assertEquals(150, ImageIO.read(new ByteArrayInputStream(
                Base64.getDecoder().decode(image.substring(image.indexOf(',') + 1)))).getWidth());
    }

    @Test
    void acceptsTrimmedCaseInsensitiveAnswerOnce() {
        String id = service.create(session).getString("captchaId");
        assertTrue(service.consume(session, id, " 2abcd "));
        assertFalse(service.consume(session, id, "2ABCD"));
    }

    @Test
    void wrongAnswerIsConsumedAndCannotBeGuessedAgain() {
        String id = service.create(session).getString("captchaId");
        assertFalse(service.consume(session, id, "WRONG"));
        assertFalse(service.consume(session, id, "2ABCD"));
    }

    @Test
    void expiresAtFiveMinuteBoundary() {
        String id = service.create(session).getString("captchaId");
        when(clock.millis()).thenReturn(301000L);
        assertFalse(service.consume(session, id, "2ABCD"));
    }

    @Test
    void refreshInvalidatesPreviousChallenge() {
        String oldId = service.create(session).getString("captchaId");
        String newId = service.create(session).getString("captchaId");
        assertNotEquals(oldId, newId);
        assertFalse(service.consume(session, oldId, "2ABCD"));
    }

    @Test
    void cannotUseChallengeInAnotherSessionOrWithoutParameters() {
        String id = service.create(session).getString("captchaId");
        assertFalse(service.consume(new MockHttpSession(), id, "2ABCD"));
        assertFalse(service.consume(null, id, "2ABCD"));
        assertFalse(service.consume(session, null, null));
        assertFalse(service.consume(session, id, "2ABCD"));
    }

    @Test
    void concurrentRequestsCannotReuseChallenge() throws Exception {
        String id = service.create(session).getString("captchaId");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch ready = new CountDownLatch(1);
        Callable<Boolean> attempt = () -> {
            ready.await();
            return service.consume(session, id, "2ABCD");
        };
        try {
            Future<Boolean> first = executor.submit(attempt);
            Future<Boolean> second = executor.submit(attempt);
            ready.countDown();
            assertNotEquals(first.get(), second.get());
        } finally {
            executor.shutdownNow();
        }
    }
}
