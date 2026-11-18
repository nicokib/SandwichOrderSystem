package be.sos.repository;

import be.sos.model.Session;
import be.sos.parser.SessionParser;
import be.sos.utils.CommonData;

public class SessionFileRepository
        extends AbstractFileRepository<Session, Long>
        implements SessionRepository {

    private static final String FILENAME = CommonData.getPathData() +"/sessions.csv";
    private static final String HEADER = "id;courseId;startDate;endDate;location;instructorId;studentsId";

    private static final SessionParser parser = new SessionParser();

    private static final SessionFileRepository INSTANCE =
            new SessionFileRepository();

    public static SessionFileRepository getInstance() {
        return INSTANCE;
    }

    private SessionFileRepository() {
        super(FILENAME, HEADER, parser,0L);
    }

    @Override
    protected Long incrementId(Long id) {
        return id + 1;
    }

    @Override
    protected String getRepositoryName() {
        return "Session";
    }

    // getAll(), find(), add(), delete() already define in super class

    @Override
    public void update(Session elt) {

        if (elt.getId() == null) {
            throw new NullPointerException("Update Session error : id cannot be null");
        }

        Session sessionToUpdate = findById(elt.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Session with id " + elt.getId() + " not found"));

        if (elements.stream().anyMatch(c ->
                !c.getId().equals(elt.getId()) && c.equals(elt))) {
            throw new IllegalArgumentException(
                    "This session already exists");
        }

        // Mise à jour des propriétés
        sessionToUpdate.setCourse(elt.getCourse());
        sessionToUpdate.setStartDate(elt.getStartDate());
        sessionToUpdate.setEndDate(elt.getEndDate());
        sessionToUpdate.setLocation(elt.getLocation());
        sessionToUpdate.setInstructor(elt.getInstructor());
        sessionToUpdate.setStudents(elt.getStudents());
        writeAllFile();
    }

}