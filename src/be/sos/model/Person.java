package be.sos.model;

import be.sos.enumeration.Language;
import be.sos.enumeration.PersonRole;
import be.sos.repository.Identifiable;

import java.util.Objects;

public class Person implements Identifiable<Integer> {
    private Integer id;
    private PersonRole role;
    private String firstName;
    private String lastName;
    private Language language;
    private String email;
    private String hashedPassword;


    public Person(Integer id, PersonRole role, String firstName, String lastName, Language language, String email, String hashedPassword) {
        Objects.requireNonNull(role, "role cannot be null");
        Objects.requireNonNull(firstName, "firstName cannot be null");
        Objects.requireNonNull(lastName, "lastName cannot be null");
        Objects.requireNonNull(language, "language cannot be null");
        Objects.requireNonNull(email, "email cannot be null");

        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonRole getRole() {
        return role;
    }

    public void setRole(PersonRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.replace(";", " ");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.replace(";", " ");
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getEmail() {
        return email.replace(";", "_");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", language=" + language +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }
}

