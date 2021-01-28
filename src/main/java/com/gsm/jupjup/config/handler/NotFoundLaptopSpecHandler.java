package com.gsm.jupjup.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class NotFoundLaptopSpecHandler {
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        response.sendRedirect("/exception/notfoundlaptopspec");
    }
}
