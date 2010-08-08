package org.ssh.app.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * ssl
 */
public class HttpsUrlRewritingInterceptor extends HandlerInterceptorAdapter {

    private int sslPort;

    public void setSslPort(int sslPort) {
        this.sslPort = sslPort;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("https".equals(request.getScheme()) == false) {
            // if it is already in https, bypass
            StringBuilder sslUrl = new StringBuilder();
            sslUrl.append("https://").append(request.getServerName()).append(":").append(sslPort).append(request.getRequestURI());

            response.sendRedirect(sslUrl.toString());
        }

        return true;
    }
}
