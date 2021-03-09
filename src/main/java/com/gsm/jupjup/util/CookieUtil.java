package com.gsm.jupjup.util;

import com.gsm.jupjup.config.security.JwtTokenProvider;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {

    /**
     * 쿠키 생성 메소드
     * @param cookieName 쿠키 이름
     * @param value 쿠키 값
     * @return 생성 된 Cookie
     */
    public Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge((int) JwtTokenProvider.TOKEN_VALIDATION_SECOND);
        token.setPath("/");
        return token;
    }

    /**
     * 쿠키를 가져오는 메소드
     * @param req 요청
     * @param cookieName 쿠키 이름
     * @return 가져온 Cookie
     */
    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

}
