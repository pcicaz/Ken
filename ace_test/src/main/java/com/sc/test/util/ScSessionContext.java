package com.sc.test.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class ScSessionContext {
    private static ScSessionContext instance;
    
    private HashMap<String, HttpSession> sessionMap;
    
    private ScSessionContext() {
        sessionMap = new HashMap<String, HttpSession>();
    }
    
    public static ScSessionContext getInstance() {
        if (instance == null) {
            instance = new ScSessionContext();
        }
        return instance;
    }
    
    public synchronized void AddSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }
    
    public synchronized void DelSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }
    
    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null){
            return null;
        }
        return (HttpSession)sessionMap.get(session_id);
    }
}
