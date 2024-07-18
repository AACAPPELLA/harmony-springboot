package com.example.harmony.interceptor;

import com.example.harmony.annotation.UserId;
import com.example.harmony.exception.CommonException;
import com.example.harmony.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final Object userIdObj = webRequest.getAttribute("USER_ID", WebRequest.SCOPE_REQUEST);

        if (userIdObj == null) {
            throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
        }

        return Long.valueOf(userIdObj.toString());
    }
}
