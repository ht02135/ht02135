package com.hung.auction.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hung.auction.controller.IAuthenticateController;
import com.hung.auction.jaxbdomain.JaxbClientSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    
    private static Logger log = Logger.getLogger(LoginInterceptor.class);
    
    private String redirectURL;

    // before the actual handler will be executed
    public synchronized boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("################################### LoginInterceptor : preHandle ###################################");
        
        HttpSession session = request.getSession(); 
        
        // check if jaxbClientSession in @SessionAttributes can be accessed via HttpSession 
        JaxbClientSession jaxbClientSession = (JaxbClientSession) session.getAttribute("jaxbClientSession");
        if (jaxbClientSession != null) {
            log.info("LoginInterceptor : jaxbClientSession="+jaxbClientSession);
        }
        
        // check if jaxbClientSession2 set in HttpSession (via LoginController) can be accessed via HttpSession here
        JaxbClientSession jaxbClientSession2 = (JaxbClientSession) session.getAttribute("jaxbClientSession2");
        if (jaxbClientSession2 != null) {
            log.info("LoginInterceptor : jaxbClientSession2="+jaxbClientSession2);
        }
        
        if (handler instanceof IAuthenticateController) {
            String loginId = (jaxbClientSession != null) ? jaxbClientSession.getLoginId() : "";
            if (loginId.equalsIgnoreCase("")) {  
                response.sendRedirect(response.encodeRedirectURL(redirectURL));
                return false; // not allow to execute handler
            }
        }
        
        return true;
    }
    
    // after the handler is executed
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    
    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

}
