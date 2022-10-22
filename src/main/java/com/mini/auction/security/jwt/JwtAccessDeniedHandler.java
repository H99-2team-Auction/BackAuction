package com.mini.auction.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini.auction.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//인가(authority) 없이 접근하려할때 403에러(FORBIDDEN)
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        ResponseEntity.status(HttpStatus.FORBIDDEN)
                )
        );
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
