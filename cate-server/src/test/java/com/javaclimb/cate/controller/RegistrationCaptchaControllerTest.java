package com.javaclimb.cate.controller;

import com.javaclimb.cate.domain.Consumer;
import com.javaclimb.cate.service.ConsumerService;
import com.javaclimb.cate.service.RegistrationCaptchaService;
import com.javaclimb.cate.service.impl.RegistrationCaptchaServiceImpl;
import com.javaclimb.cate.utils.CipherBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationCaptchaControllerTest {
    private ConsumerService consumers;
    private RegistrationCaptchaService captchas;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        consumers = mock(ConsumerService.class);
        captchas = mock(RegistrationCaptchaService.class);
        ConsumerController controller = new ConsumerController();
        CipherBean cipher = new CipherBean();
        cipher.setKey("0123456789abcdef0123456789abcdef");
        ReflectionTestUtils.setField(controller, "cipher", cipher);
        ReflectionTestUtils.setField(controller, "consumerService", consumers);
        ReflectionTestUtils.setField(controller, "registrationCaptchaService", captchas);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void directRegistrationWithoutCaptchaIsRejectedBeforeDatabaseAccess() throws Exception {
        mvc.perform(post("/consumer/add"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("验证码错误或已过期，请刷新后重试"));
        verifyNoInteractions(consumers);
    }

    @Test
    void invalidCaptchaCannotCreateAccount() throws Exception {
        MockHttpSession session = new MockHttpSession();
        mvc.perform(post("/consumer/add").session(session)
                .param("captchaId", "id").param("captchaCode", "WRONG"))
                .andExpect(jsonPath("$.code").value(0));
        verify(captchas).consume(session, "id", "WRONG");
        verifyNoInteractions(consumers);
    }

    @Test
    void verifiedRegistrationReachesExistingAccountCreation() throws Exception {
        MockHttpSession session = new MockHttpSession();
        when(captchas.consume(session, "id", "2ABCD")).thenReturn(true);
        when(consumers.insert(any(Consumer.class))).thenReturn(true);
        mvc.perform(post("/consumer/add").session(session)
                .param("captchaId", "id").param("captchaCode", "2ABCD")
                .param("username", "captcha-test").param("password", "test-password")
                .param("sex", "1").param("phoneNum", "test-phone")
                .param("email", "test@example.com").param("birth", "2000-01-01")
                .param("introduction", "").param("location", "").param("avator", "/img/user.jpg"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value(1));
        ArgumentCaptor<Consumer> saved = ArgumentCaptor.forClass(Consumer.class);
        verify(consumers).insert(saved.capture());
        assertEquals("captcha-test", saved.getValue().getUsername());
    }

    @Test
    void imageEndpointDisablesCachingAndCreatesSession() throws Exception {
        when(captchas.create(any())).thenAnswer(invocation ->
                new RegistrationCaptchaServiceImpl().create(invocation.getArgument(0)));
        mvc.perform(get("/consumer/captcha"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "no-store, no-cache, must-revalidate"))
                .andExpect(jsonPath("$.captchaId").isNotEmpty())
                .andExpect(jsonPath("$.image").isNotEmpty())
                .andExpect(jsonPath("$.answer").doesNotExist());
    }
}
