package be.sos.repository;

public interface Identifiable<ID> {
    ID getId();
    void setId(ID id);
}
