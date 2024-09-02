package com.wavemaker.leave_management.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class UserDetails {
    public static int getUserId(HttpServletRequest request) {
        int userId = 0;
        HttpSession session = request.getSession(true);
        Object cookieValue = session.getAttribute("userCookie");
        if (cookieValue != null) {
            userId = Integer.parseInt(cookieValue.toString());
        }
        return userId;
    }
}
