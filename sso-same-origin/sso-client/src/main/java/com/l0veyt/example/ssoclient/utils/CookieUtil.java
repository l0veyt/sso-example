package com.l0veyt.example.ssoclient.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    private CookieUtil() {
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || StringUtils.isEmpty(name)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static void removeCookie(HttpServletResponse response, String name,
                                    String path, String domain) {

        Cookie cookie = new Cookie(name, null);

        if (path != null) {
            cookie.setPath(path);
        }

        if (domain != null) {
            cookie.setDomain(domain);
        }

        cookie.setMaxAge(-1000);
        response.addCookie(cookie);
    }
}
