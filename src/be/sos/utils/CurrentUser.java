package be.sos.utils;

import be.sos.model.Person;
import be.sos.model.Session;
import be.sos.service.SessionService;

import java.time.LocalDate;
import java.util.List;


// Can be called everywhere in the Java program (to avoid in the model class)
public class CurrentUser {
    private static Person user;
    private static Session currentSession;
    private static List<Session> activeSessions;

    public static Person getUser() {
        return user;
    }

    public static void setUser(Person newUser) {
        user = newUser;
    }

    public static List<Session> getActiveSessions() {
        return activeSessions;
    }

    public static void initCurrentSession() {
        activeSessions = SessionService.getSessionOfAnUser(user, LocalDate.now());
    }
}
