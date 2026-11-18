package be.sos.service;

import be.sos.model.Person;
import be.sos.model.Session;
import be.sos.repository.SessionFileRepository;
import be.sos.repository.SessionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SessionService {
    private static final SessionRepository sessionRepository = SessionFileRepository.getInstance();

    public static List<Session> getSessionOfAnUser(Person user, LocalDate date) {
        return sessionRepository.findAll().stream()
                .filter(s -> (!date.isBefore(s.getStartDate())) &&  (!date.isAfter(s.getEndDate())))
                .filter(s -> user.equals(s.getInstructor()) || s.getStudents().contains(user))
                .collect(Collectors.toList());
    }
}
