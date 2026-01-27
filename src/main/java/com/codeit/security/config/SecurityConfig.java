package com.codeit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security의 보안 필터 체인을 구성하고 조립하는 역할 (절차 규칙 설정)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 특정 경로에 대한 권한을 설정
                .authorizeHttpRequests(auth ->
                auth.requestMatchers("/", "/h2-console/**", "/signup", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 로그인 폼 설정 (REST에서는 사용하지 않습니다)
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지 경로
                        .permitAll())
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // H2는 CSRF 검증 제외
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // 같은 사이트에서 iframe을 사용하는 것을 허용해 달라.
                );

        return http.build();
    }

}
