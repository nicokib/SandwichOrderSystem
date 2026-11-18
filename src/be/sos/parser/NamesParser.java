package be.sos.parser;

import be.sos.enumeration.Language;
import be.sos.model.Course;

import java.util.EnumMap;
import java.util.Map;
import java.util.StringJoiner;

public class NamesParser implements Parser< Map<Language, String> > {

    @Override
    public Map<Language, String> parse(String input) {
        Language[] langues = Language.values(); // Prend l'ordre exact de l'enum
        String[] parts = input.split("/");
        Map<Language, String> names = new EnumMap<>(Language.class);
        int count = Math.min(parts.length, langues.length);
        for (int i = 0; i < count; i++) {
            String translation = parts[i].trim();
            if (!translation.isEmpty()) {
                names.put(langues[i], translation);
            }
        }
        return names;

    }


    @Override
    public String format(Map<Language, String> names) {
        StringJoiner sj = new StringJoiner("/");
        for (Language lang : Language.values()) {
            sj.add(names.get(lang));
        }
        return sj.toString();
    }
}
