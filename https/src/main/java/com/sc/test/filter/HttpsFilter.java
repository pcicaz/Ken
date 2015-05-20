package com.sc.test.filter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sc.test.util.HttpsPostUtil;

public class HttpsFilter implements Filter {
    
    @Override
    public void destroy() {
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        
        final HttpServletRequest httpRequest = (HttpServletRequest)request;
        Enumeration<String> names = httpRequest.getAttributeNames();
        StringBuffer data = new StringBuffer();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Object obj = httpRequest.getAttribute(name);
            data.append(name).append("=").append(String.valueOf(obj)).append(";");
        }
        
        StringBuffer url = httpRequest.getRequestURL();
        HttpsURLConnection urlCon = HttpsPostUtil.getInstance().httpsPostConnection(url.toString());
        urlCon.setRequestMethod("POST");
        urlCon.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(urlCon.getOutputStream());
        if (data != null)
            out.writeBytes(data.toString());
        
        out.flush();
        out.close();
        
        // final HttpSession session = httpRequest.getSession(false);
        //
        // if (session != null) {
        // final Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        // sessionCookie.setMaxAge(-1);
        // sessionCookie.setSecure(false);
        // sessionCookie.setPath(httpRequest.getContextPath());
        // httpResponse.addCookie(sessionCookie);
        // }
        
//        chain.doFilter(request, response);
        
    }
    
    @Override
    public void init(FilterConfig arg0)
        throws ServletException {
    }
    
}
