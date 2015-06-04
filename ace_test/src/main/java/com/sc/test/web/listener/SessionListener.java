package com.sc.test.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sc.test.util.ScSessionContext;

public class SessionListener implements HttpSessionListener {
    
    private   ScSessionContext sc= ScSessionContext.getInstance();
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sc.AddSession(se.getSession());
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sc.DelSession(session);
    }
    
}
