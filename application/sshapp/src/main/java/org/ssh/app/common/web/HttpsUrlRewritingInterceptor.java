package org.ssh.app.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * ssl
 */
public class HttpsUrlRewritingInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpsUrlRewritingInterceptor.class);

    private int sslPort;

    public void setSslPort(int sslPort) {
        this.sslPort = sslPort;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("https".equals(request.getScheme()) == false) {

            logger.debug("pre" + request.getRequestURI());

            // if it is already in https, bypass
            StringBuilder sslUrl = new StringBuilder();
            sslUrl.append("https://").append(request.getServerName()).append(":").append(sslPort).append(request.getRequestURI());

            response.sendRedirect(sslUrl.toString());
        }

        return true;
    }

    /**
     * This implementation is empty.
     */
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        logger.debug("post" + request.getRequestURI());

    }

    /**
     * This implementation is empty.
     */
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.debug("after"+ request.getRequestURI());
    }

}
