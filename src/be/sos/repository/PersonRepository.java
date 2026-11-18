package be.sos.repository;

import be.sos.model.Person;

import java.util.Optional;

public interface PersonRepository extends Repository<Person, Integer> {
    Optional<Person> findByEmail(String email);
}
