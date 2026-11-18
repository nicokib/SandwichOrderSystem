package be.sos.service;

import be.sos.model.Person;
import be.sos.repository.PersonFileRepository;
import be.sos.repository.PersonRepository;
import be.sos.utils.PasswordUtils;

import java.util.Optional;

public class PersonService {

    private static final PersonRepository personRepository = PersonFileRepository.getInstance();

    public static Optional<Person> validateCredentials(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return Optional.empty();
        }

        String normalizedEmail = username.trim();
        return personRepository.findAll()
                .stream()
                .filter(p -> p.getEmail() != null && p.getEmail().equalsIgnoreCase(normalizedEmail))
                .filter(p -> PasswordUtils.checkPassword(password, p.getHashedPassword()))
                .findFirst();
    }
}
