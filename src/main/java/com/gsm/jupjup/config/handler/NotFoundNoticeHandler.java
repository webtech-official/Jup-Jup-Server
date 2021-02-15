package com.gsm.jupjup.config.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFoundNoticeHandler {
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        response.sendRedirect("/exception/NotFoundNoticeException");
    }
}
