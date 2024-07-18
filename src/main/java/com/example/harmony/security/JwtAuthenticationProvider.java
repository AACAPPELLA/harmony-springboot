package com.example.harmony.security;

import com.example.harmony.security.info.JwtUserInfo;
import com.example.harmony.security.info.UserPrincipal;
import com.example.harmony.security.service.CustomUserDetailService;
import com.example.harmony.type.ERole;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserPrincipal userPrincipal = null;
        if (authentication instanceof JwtAuthenticationToken) {
            JwtUserInfo jwtUserInfo = new JwtUserInfo((Long) authentication.getPrincipal(), (ERole) authentication.getCredentials());
            userPrincipal = (UserPrincipal) customUserDetailService.loadUserByUserId(jwtUserInfo.id());

            if (userPrincipal.getRole() != jwtUserInfo.role()) {
                throw new AuthenticationException("Invalid Role") {
                };
            }
        } else if (authentication instanceof UsernamePasswordAuthenticationToken){
            //기본로그인의 경우
            userPrincipal = (UserPrincipal) customUserDetailService.loadUserByUsername((String) authentication.getPrincipal());
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), userPrincipal.getPassword())) {
                throw new BadCredentialsException("Invalid password!");
            }
        }


        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class)
                || authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
