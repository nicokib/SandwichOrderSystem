package be.sos.test;

import be.sos.repository.Identifiable;
import be.sos.repository.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryTest {

    public <T extends Identifiable<ID>, ID> void testCRUD(
            Repository<T, ID> repository,
            T objectToAdd,
            T updatedObject) {

        int initialSize = repository.getSize();
        System.out.println("Initial size of " + objectToAdd.getClass().getSimpleName() + "  : " + initialSize);

        //ADD
        T createdObject = repository.add(objectToAdd);
        assertEquals(initialSize + 1, repository.getSize());
        System.out.println("Add to Repository " + repository.getClass().getSimpleName() + " with id " + createdObject.getId() + " : " + createdObject);

        // UPDATE
        ID createdObjectId = createdObject.getId();
        updatedObject.setId(createdObjectId);
        repository.update(updatedObject);
        System.out.println("Update in Repository " + repository.getClass().getSimpleName());

        // FIND
        T readedObject = repository.findById(createdObjectId).orElseThrow(() -> new RuntimeException("Object not found in Repository " + repository.getClass().getSimpleName()));
        System.out.println("FindById in Repository " + repository.getClass().getSimpleName() + " with ID " + createdObjectId +  " : " + readedObject);

        // DELETE
        repository.delete(createdObjectId);
        assertEquals(initialSize, repository.getSize());
        System.out.println("delete in Repository " + repository.getClass().getSimpleName() + " with ID " + createdObjectId);
    }

}
