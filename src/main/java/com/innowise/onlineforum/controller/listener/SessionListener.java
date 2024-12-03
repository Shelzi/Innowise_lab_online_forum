package com.innowise.onlineforum.controller.listener;

import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.model.entity.UserRole;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.Locale;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        //HttpSession session = sessionEvent.getSession();
        //session.setAttribute(SessionAttribute.CURRENT_LOCALE, new Locale("ru", "RU"));
        //session.setAttribute(SessionAttribute.CURRENT_ROLE, UserRole.GUEST);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().invalidate();
    }
}