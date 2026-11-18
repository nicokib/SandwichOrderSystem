package be.sos.parser;

import be.sos.enumeration.Language;
import be.sos.enumeration.PersonRole;
import be.sos.model.Person;

public class PersonParser implements Parser<Person> {

    // csv format : id;role;firstname;lastname;language;email;hashedPassword

    @Override
    public Person parse(String line)  {
        int idxCol = 0;
        String [] parts = line.split(";", - 1);

        if (parts.length < 7) {
            throw new IllegalArgumentException("Format de ligne invalide pour Person : " + line);
        }

        Integer id = Integer.parseInt(parts[idxCol++]);
        PersonRole role =  PersonRole.valueOf(parts[idxCol++].trim().toUpperCase());
        String firstName = parts[idxCol++];
        String lastName = parts[idxCol++];
        Language language = Language.valueOf(parts[idxCol++].trim().toUpperCase());
        String email = parts[idxCol++];
        String hashedPassword = parts[idxCol++];
        return new Person(id, role, firstName, lastName, language, email, hashedPassword);
    }

    @Override
    public String format(Person i) {
        return String.join(";",
                String.valueOf(i.getId()),
                String.valueOf(i.getRole()),
                i.getFirstName(),
                i.getLastName(),
                String.valueOf(i.getLanguage()),
                i.getEmail(),
                i.getHashedPassword()
        );
    }

}
