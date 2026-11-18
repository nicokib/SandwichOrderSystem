package be.sos.repository;

import be.sos.model.Person;
import be.sos.parser.PersonParser;
import be.sos.utils.CommonData;

import java.util.Optional;

public class PersonFileRepository
        extends AbstractFileRepository<Person, Integer>
        implements PersonRepository {

    private static final String FILENAME = CommonData.getPathData() + "/persons.csv";
    private static final String HEADER = "id;role;firstname;lastname;language;email;hashedPassword";

    private static final PersonParser parser = new PersonParser();

    private static final PersonFileRepository INSTANCE =
            new PersonFileRepository();

    public static PersonFileRepository getInstance() {
        return INSTANCE;
    }

    private PersonFileRepository() {
        super(FILENAME, HEADER, parser,0);
    }

    @Override
    protected Integer incrementId(Integer id) {
        return id + 1;
    }

    @Override
    protected String getRepositoryName() {
        return "Person";
    }

    // getAll(), find(), add(), delete() already define in super class

    @Override
    public Optional<Person> findByEmail(String email) {
        return elements.stream()
                .filter(element -> element.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void update(Person elt) {
        if(elt.getId() == null) throw new NullPointerException("Update Person error : the id cannot be null");
        Person PersonToUpdate = findById(elt.getId()).orElseThrow(() -> new IllegalArgumentException("Update Person error : Person with id " + elt.getId() + " not found"));

        if(elements.stream().anyMatch(i -> !i.getId().equals(elt.getId()) && i.equals(elt))) throw new IllegalArgumentException("Update Person error : Person already exists be unique");

        PersonToUpdate.setRole(elt.getRole());
        PersonToUpdate.setFirstName(elt.getFirstName());
        PersonToUpdate.setLastName(elt.getLastName());
        PersonToUpdate.setLanguage(elt.getLanguage());
        PersonToUpdate.setEmail(elt.getEmail());
        PersonToUpdate.setHashedPassword(elt.getHashedPassword());

        writeAllFile();
    }

}