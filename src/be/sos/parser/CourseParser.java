package be.sos.parser;

import be.sos.model.Course;

public class CourseParser implements Parser<Course> {
    // csv format : id;title

    @Override
    public Course parse(String line)  {
        int idxCol = 0;
        String [] parts = line.split(";", - 1);

        if (parts.length < 2) {
            throw new IllegalArgumentException("Format de ligne invalide pour Course : " + line);
        }

        Integer id = Integer.parseInt(parts[idxCol++].trim());
        String title = parts[idxCol++].trim();

        return new Course(id, title);
    }

    @Override
    public String format(Course c) {
        return String.join(";",
                String.valueOf(c.getId()),
                c.getTitle()
        );
    }
}
