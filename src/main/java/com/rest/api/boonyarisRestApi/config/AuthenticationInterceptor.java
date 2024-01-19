package com.rest.api.boonyarisRestApi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.model.response.Response;
import com.rest.api.boonyarisRestApi.service.JwtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(AuthenticationInterceptor.class);

    private final JwtService jwtService;

    @Autowired
    public AuthenticationInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // โค้ดที่จะทำงานก่อนที่ Controller จะถูกเรียก
        try {
            logger.debug("Pre Handle method is Calling");
            this.jwtService.checkAccessToken(request.getHeader("token"));
        } catch (ResponseException e) {
            logger.info(e.getMessage(), e);
            response.setHeader(Constant.CONTENT_TYPE, Constant.CONTENT_TYPE_JSON);
            PrintWriter out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(new Response(false, e.getExceptionCode(), e.getMessage(), null));
            out.print(json);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // โค้ดที่จะทำงานหลังจาก Response ถูกส่งกลับไปแล้ว
        logger.debug("Request and Response is completed");
    }
}
