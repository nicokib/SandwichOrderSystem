package be.sos.model;

import be.sos.repository.Identifiable;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Session  implements Identifiable<Long> {
    private Long id;
    private Course course;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private Person instructor;
    private List<Person> students;

    public Session(Long id, Course course, LocalDate startDate, LocalDate endDate, String location, Person instructor, List<Person> students) {

        if(course == null) throw new NullPointerException("course cannot be null");
        if(startDate == null) throw new NullPointerException("startDate cannot be null");
        if(endDate == null) throw new NullPointerException("endDate cannot be null");
        if(id == null) throw new NullPointerException("id cannot be null");
        if(id == null) throw new NullPointerException("id cannot be null");


        this.id = id;
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.instructor = instructor;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location.replace(";", " ");
    }

    public Person getInstructor() {
        return instructor;
    }

    public void setInstructor(Person instructor) {
        this.instructor = instructor;
    }

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
        students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Session session)) return false;
        return Objects.equals(course, session.course) && Objects.equals(startDate, session.startDate) && Objects.equals(endDate, session.endDate) && Objects.equals(location, session.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, startDate, endDate, location);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", course=" + course +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", instructor=" + instructor +
                ", students=" + students +
                '}';
    }
}
