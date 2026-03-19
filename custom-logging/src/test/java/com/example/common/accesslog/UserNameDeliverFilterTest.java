package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {UserNameDeliverFilter.class})
@TestPropertySource(properties = "spring.security.filter.order=100")
public class UserNameDeliverFilterTest {

    @Autowired
    UserNameDeliverFilter sut;

    @Test
    void 認証済みユーザー名をリクエストへセットする() throws Exception {

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        sut.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(authentication).getName();
        verify(request).setAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME, "testuser");
        verifyNoMoreInteractions(request, response, chain);
    }

    @Test
    void 認証済みユーザー名が取得できない場合はリクエストへセットしない() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        sut.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verifyNoMoreInteractions(request, response, chain);
    }

    @Test
    void フィルターの順序() {

        int order = sut.getOrder();

        assertEquals(101, order);
    }
}
