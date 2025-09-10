package com.ig.banking_system.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.banking_system.exceptions.ErrorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorException exception = new ErrorException();
        log.error("==> Error: {}", exception.getMessage());
        exception.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        exception.setMessage(
                request.getAttribute("invalid") != null
                        ? request.getAttribute("invalid").toString()
                        : authException.getLocalizedMessage()
        );

        if (response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            exception.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            exception.setMessage("System Error");
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        var responseMap = new HashMap<String, Object>();
        responseMap.put("response", exception);
        OutputStream out = response.getOutputStream();
        new ObjectMapper().writeValue(out, responseMap);
        out.flush();
    }
}