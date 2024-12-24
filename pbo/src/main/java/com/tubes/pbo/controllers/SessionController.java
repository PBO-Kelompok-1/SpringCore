package com.tubes.pbo.controllers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {

    @GetMapping("/session-info")
    public Map<String, Object> getSessionInfo(HttpSession session) {
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("id", session.getId());
        sessionInfo.put("creationTime", session.getCreationTime());
        sessionInfo.put("lastAccessedTime", session.getLastAccessedTime());
        sessionInfo.put("timeout", session.getMaxInactiveInterval() + " seconds"); // Menambahkan timeout

        // Get session attributes
        Map<String, Object> attributes = new HashMap<>();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            attributes.put(attributeName, session.getAttribute(attributeName));
        }
        sessionInfo.put("attributes", attributes);

        return sessionInfo;
    }
}
