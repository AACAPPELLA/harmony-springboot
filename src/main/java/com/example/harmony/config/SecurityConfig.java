package com.example.harmony.config;

import com.example.harmony.constant.Constant;
import com.example.harmony.security.JwtAuthEntryPoint;
import com.example.harmony.security.JwtAuthenticationProvider;
import com.example.harmony.security.filter.JwtAuthenticationFilter;
import com.example.harmony.security.filter.JwtExceptionFilter;
import com.example.harmony.security.handler.*;
import com.example.harmony.security.service.CustomUserDetailService;
import com.example.harmony.type.ERole;
import com.example.harmony.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSucessHandler defaultSuccessHandler;
    private final DefaultFaiureHandler defaultFailureHandler;

    private final CustomUserDetailService customUserDetailService;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final CustomLogOutProcessHandler customLogOutProcessHandler;
    private final CustomLogOutResultHandler customLogOutResultHandler;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {


        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/**").hasAnyRole((ERole.USER.toString()),ERole.ADMIN.toString())
                        .requestMatchers("/admin/**").hasRole(ERole.ADMIN.toString())
                        .anyRequest().authenticated())
                .formLogin(configurer ->
                                configurer
                                        .loginProcessingUrl("/auth/login")
                                        .usernameParameter("serialId")
                                        .passwordParameter("password")
                                        .successHandler(defaultSuccessHandler)
                                        .failureHandler(defaultFailureHandler)
                )
                .logout(configurer ->
                        configurer
                                .logoutUrl("/auth/logout")
                                .addLogoutHandler(customLogOutProcessHandler)
                                .logoutSuccessHandler(customLogOutResultHandler)
                                .deleteCookies(Constant.AUTHORIZATION, Constant.REAUTHORIZATION)
                )
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )


                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, new JwtAuthenticationProvider(customUserDetailService,passwordEncoder)), LogoutFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
                .getOrBuild();
    }
}
