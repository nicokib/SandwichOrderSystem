package be.sos.parser;

import be.sos.enumeration.Language;
import be.sos.model.TypeSandwich;

import java.util.*;

public class TypeSandwichParser implements Parser<TypeSandwich> {

    // csv format : id;names

    private static NamesParser namesParser = new NamesParser();

    @Override
    public TypeSandwich parse(String line)  {
        int idxCol = 0;
        String [] parts = line.split(";", - 1);

        if (parts.length < 2) {
            throw new IllegalArgumentException("Format de ligne invalide pour Sandwich : " + line);
        }

        Integer id = Integer.parseInt(parts[idxCol++].trim());
        Map<Language, String> names = namesParser.parse(parts[idxCol++].trim());
        return new TypeSandwich(id, names);
    }

    @Override
    public String format(TypeSandwich s) {
        StringJoiner sj = new StringJoiner(";");
        sj.add(String.valueOf(s.getId()));
        sj.add(namesParser.format(s.getNames()));
        return sj.toString();
    }
}
