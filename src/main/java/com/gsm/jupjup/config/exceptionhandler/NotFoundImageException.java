package com.gsm.jupjup.config.exceptionhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class NotFoundImageException extends Throwable {
    public void commence(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        response.sendRedirect("/exception/imagenotfound");
    }
}
