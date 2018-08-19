package com.l0veyt.example.ssoclient.filter;

import com.l0veyt.example.ssoclient.pojo.User;
import com.l0veyt.example.ssoclient.utils.CookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@WebFilter(filterName = "SsoFilter", urlPatterns = "/*")
public class SsoFilter implements Filter {

    // SSO Server登录页面URL
    private static final String SSO_LOGIN_URL = "/server/user/loginPage";

    private static final String SSO_VALIDATE_URL = "http://localhost:8080/server/validate";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取Token
        String token = CookieUtil.getCookie(request, "token");
        // 获取请求URL
        String originUrl = request.getRequestURL().toString();
        // 获取查询字段
        String queryString = request.getQueryString();
        if (queryString != null) {
            originUrl = originUrl + "?" + queryString;
        }

        // token 不存在，跳转到SSOServer用户登录页
        if (token == null) {
            response.sendRedirect(SSO_LOGIN_URL + "?originUrl=" + URLEncoder.encode(originUrl, "utf-8"));
        } else { // token存在，验证有效性
            URL validateUrl = new URL(SSO_VALIDATE_URL + "?token=" + token);
            HttpURLConnection conn = (HttpURLConnection) validateUrl
                    .openConnection();

            conn.connect();
            InputStream is = conn.getInputStream();

            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            String ret = new String(buffer);

            if (ret.length() == 0) { // 返回空字符串，表示 token无效
                response.sendRedirect(SSO_LOGIN_URL + "?originUrl=" + URLEncoder.encode(originUrl, "utf-8"));
            } else {
                String[] tmp = ret.split(";");
                User user = new User();
                for (String aTmp : tmp) {
                    String[] attrs = aTmp.split("=");
                    switch (attrs[0]) {
                        case "id":
                            user.setId(Integer.parseInt(attrs[1]));
                            break;
                        case "name":
                            user.setName(attrs[1]);
                            break;
                        case "username":
                            user.setAccount(attrs[1]);
                            break;
                    }
                }
                request.setAttribute("user", user);
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
