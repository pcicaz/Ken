package com.sc.test.web.interceptor;

import com.sc.test.bean.ResponseMessage;
import com.sc.test.bean.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Feb 08, 2015    Steven
 */
@Component
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {
    private Log logger = LogFactory.getLog(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();

        //check if there's an user logged in.
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("currUser");
        if (user == null || user.getUsername() == null) {
            logger.debug("no user logged in...");
            if (requestUri.endsWith(".html")) {
                response.getWriter().write("<script>location.href=\"\\login\"</script>");
                return false;
            } else {
                response.getWriter().write("{\"success\":false, \"code\":" + ResponseMessage.ERR_NOT_LOGGED + "}");
                return false;
            }
        }
        if (!requestUri.endsWith(".html")) {
            /*if (!privilegeService.isAuthorized(requestUri, user)) {
                throw new NoPrivilegeException();
            }*/

            //todo
        }
        return true;
    }
}
