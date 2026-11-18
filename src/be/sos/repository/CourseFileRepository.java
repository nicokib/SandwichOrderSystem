package be.sos.repository;

import be.sos.model.Course;
import be.sos.parser.CourseParser;
import be.sos.utils.CommonData;

public class CourseFileRepository
        extends AbstractFileRepository<Course, Integer>
        implements CourseRepository {

    private static final String FILENAME = CommonData.getPathData() + "/courses.csv";
    private static final String HEADER = "id;title";

    private static final CourseParser parser = new CourseParser();

    private static final CourseFileRepository INSTANCE =
            new CourseFileRepository();

    public static CourseFileRepository getInstance() {
        return INSTANCE;
    }

    private CourseFileRepository() {
        super(FILENAME, HEADER, parser,0);
    }

    @Override
    protected Integer incrementId(Integer id) {
        return id + 1;
    }

    @Override
    protected String getRepositoryName() {
        return "Course";
    }

    // getAll(), find(), add(), delete() already define in super class

    @Override
    public void update(Course elt) {

        if (elt.getId() == null) {
            throw new NullPointerException("Update Course error : id cannot be null");
        }

        Course courseToUpdate = findById(elt.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Course with id " + elt.getId() + " not found"));

        if (elements.stream().anyMatch(c ->
                !c.getId().equals(elt.getId()) && c.equals(elt))) {
            throw new IllegalArgumentException(
                    "Course title " + elt.getTitle() + " already exists");
        }

        // Mise à jour des propriétés
        courseToUpdate.setTitle(elt.getTitle());

        writeAllFile();
    }

}