package com.dante.passec.utils;

import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Util class for git List session.
 */
public class SessionManager {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date = new Date();
    public static List<Session> getSessionList(List<UserRest> userRests){
        Session session = new Session(userRests.get(0));
        Session session2 = new Session(userRests.get(1));
        List<Session> sessions = Arrays.asList(session, session2);
        return sessions;
    }
}
