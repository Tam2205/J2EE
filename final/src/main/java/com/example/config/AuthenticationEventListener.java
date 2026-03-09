package com.example.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.model.ActivityLog;
import com.example.repository.ActivityLogRepository;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationEventListener {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setAction("LOGIN");
        log.setDescription("Dang nhap thanh cong");
        log.setTimestamp(LocalDateTime.now());
        log.setIpAddress(getClientIp());
        activityLogRepository.save(log);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setAction("LOGIN_FAILED");
        log.setDescription("Dang nhap that bai");
        log.setTimestamp(LocalDateTime.now());
        log.setIpAddress(getClientIp());
        activityLogRepository.save(log);
    }

    private String getClientIp() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                return request.getRemoteAddr();
            }
        } catch (Exception ignored) {}
        return "unknown";
    }
}
