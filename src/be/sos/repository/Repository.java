package be.sos.repository;

import be.sos.model.Course;
import be.sos.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    List<T> findByIds(List<ID> lstIds);
    T add(T elt);
    T addWithId(T elt);
    void update(T elt);
    void delete(ID id);
    int getSize();
    String getHeaderFile();
}