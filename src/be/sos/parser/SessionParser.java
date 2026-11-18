package be.sos.parser;

import be.sos.model.Course;
import be.sos.model.Person;
import be.sos.model.Session;
import be.sos.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class SessionParser implements Parser<Session> {
    // csv format : id;courseId;startDate;endDate;location;instructorId;studentsId
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static CourseRepository courseRepository = CourseFileRepository.getInstance();
    private static PersonRepository personRepository = PersonFileRepository.getInstance();

    @Override
    public Session parse(String line) {
        int idxCol = 0;
        String[] parts = line.split(";", - 1);

        if (parts.length < 7) {
            throw new IllegalArgumentException("Format de ligne invalide pour Course : " + line);
        }

        Long id = Long.parseLong(parts[idxCol++].trim());
        int courseId = Integer.parseInt(parts[idxCol++].trim());
        Course course =  courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Cours introuvable pour l'id " + courseId));
        LocalDate startDate = LocalDate.parse(parts[idxCol++].trim(), dtf);
        LocalDate endDate = LocalDate.parse(parts[idxCol++].trim(), dtf);
        String location = parts[idxCol++].trim();
        Integer instructorId = Integer.parseInt(parts[idxCol++].trim());
        Person instructor = personRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Personne introuvable pour l'id " + instructorId));

        List<Person> students = Arrays.stream(parts[idxCol++].trim().split(","))
                .map(String::trim) // Removes accidental spaces
                .map(Integer::parseInt)
                .map(idPerson -> personRepository.findById(idPerson))
                .flatMap(Optional::stream) // Filter the empties and extract the Ingredient in one time
                .collect(Collectors.toList());

        return new Session(id,course, startDate, endDate, location, instructor, students);
    }

    @Override
    public String format(Session s) {
        StringJoiner sj = new StringJoiner(";");

        sj.add(String.valueOf(s.getId()));
        sj.add(String.valueOf(s.getCourse().getId()));
        sj.add(s.getStartDate().format(dtf));
        sj.add(s.getEndDate().format(dtf));
        sj.add(s.getLocation());
        sj.add(String.valueOf(s.getInstructor().getId()));
        String studentIds;
        if(s.getStudents() != null) {
            studentIds = s.getStudents().stream()
                    .map(ing -> String.valueOf(ing.getId()))
                    .collect(Collectors.joining(","));
        } else studentIds = "";
        sj.add(studentIds);

        return sj.toString();
    }
}