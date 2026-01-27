package com.codeit.security.security.event;

import com.codeit.security.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AuthenticationFailureListener {

    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {

        String username = event.getAuthentication().getName();
        String reason = event.getException().getMessage();

        log.warn("=== 로그인 실패 ===");
        log.info("사용자: {}", username);
        log.info("이유: {}", reason);
        log.info("시간: {}", LocalDateTime.now());

        // 특정 실패 유형별 처리
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            // 비밀번호 오류 (실패 횟수 체크해서 계정 잠금 로직 가능)
            log.warn("비밀번호 오류");
        } else if (event instanceof AuthenticationFailureDisabledEvent) {
            // 비활성화된 계정
            log.warn("비활성화된 계정!");
        }

    }

}













