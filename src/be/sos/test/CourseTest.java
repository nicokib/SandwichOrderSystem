package be.sos.test;

import be.sos.model.Course;
import be.sos.repository.CourseFileRepository;
import be.sos.repository.CourseRepository;
import org.junit.jupiter.api.Test;

public class CourseTest extends RepositoryTest {
    @Test
    public void testCourseCRUD() {
        CourseRepository courseRepository = CourseFileRepository.getInstance();
        testCRUD(courseRepository,
                new Course(null, "Test CRUD"),
                new Course(null, "Test CRUD Updated")
        );
    }
}
