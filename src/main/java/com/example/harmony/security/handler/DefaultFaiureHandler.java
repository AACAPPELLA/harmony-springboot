package com.example.harmony.security.handler;

import com.example.harmony.exception.ErrorCode;
import com.example.harmony.security.AbstractAuthenticationFailure;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultFaiureHandler extends AbstractAuthenticationFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        setErrorResponse(response, ErrorCode.FAILURE_LOGIN);
    }
}
