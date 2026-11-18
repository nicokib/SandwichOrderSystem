package be.sos.repository;

import be.sos.parser.Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractFileRepository<T extends Identifiable<ID>, ID extends Comparable<ID>> implements Repository<T, ID> {

protected final Path filename;
    protected final String headerFile;

    protected List<T> elements = new ArrayList<>();
    protected ID lastId;
    protected Parser<T> parser;

    protected AbstractFileRepository(String filename, String headerFile, Parser<T> parser, ID initialId) {
        this.filename = Path.of(filename);
        this.headerFile = headerFile;
        this.parser = parser;
        this.lastId = initialId;

        loadAllFile();
    }

    protected abstract ID incrementId(ID id);

    protected abstract String getRepositoryName();

    @Override
    public String getHeaderFile(){
        return headerFile;
    }

    @Override
    public int getSize(){
        return elements.size();
    }

    @Override
    public List<T> findAll() {
        return elements;
    }

    @Override
    public Optional<T> findById(ID id) {
        return elements.stream()
                .filter(element -> element.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<T> findByIds(List<ID> lstIds) {
        return elements.stream()
                .filter(element -> lstIds.contains(element.getId()))
                .toList();
    }

    @Override
    public T add(T elt) {
        if (elt.getId() != null) {
            throw new IllegalArgumentException(
                    "Add " + getRepositoryName() + " error : id must be null");
        }

        lastId = incrementId(lastId);
        elt.setId(lastId);

        elements.add(elt);
        appendRecordToFile(elt);

        return elt;
    }

    @Override
    public T addWithId(T elt) {
        ID id = elt.getId();

        if (id == null) {
            throw new NullPointerException(
                    "Add with forced ID " + getRepositoryName() + " error : id cannot be null");
        }

        if (findById(id).isPresent()) {
            throw new IllegalArgumentException(
                    getRepositoryName() + " with id " + id + " already exists");
        }

        elements.add(elt);

        if (id.compareTo(lastId) > 0) {
            lastId = id;
        }

        appendRecordToFile(elt);

        return elt;
    }

    @Override
    public void delete(ID id) {
        if (id == null) {
            throw new NullPointerException(
                    "Delete " + getRepositoryName() + " error : id cannot be null");
        }

        T element = findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        getRepositoryName() + " with id " + id + " not found"));

        elements.remove(element);
        writeAllFile();
    }

    protected void loadAllFile() {
        try (Stream<String> lines = Files.lines(filename)) {

            elements = lines
                    .skip(headerFile == null ? 0 : 1)
                    .filter(line -> !line.isBlank())
                    .map(parser::parse)
                    .collect(Collectors.toList());

            lastId = elements.stream()
                    .map(T::getId)
                    .max(ID::compareTo)
                    .orElse(lastId);

        } catch (IOException e) {
            throw new RuntimeException(
                    getRepositoryName() + " Load File Error : " + e.getMessage(), e);
        }
    }

    protected void writeAllFile() {
        List<String> lines = new ArrayList<>();

        if(headerFile != null) lines.add(headerFile);

        for (T element : elements) {
            lines.add(parser.format(element));
        }

        try {
            Files.write(filename, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(
                    getRepositoryName() + " Write File Error", e);
        }
    }

    protected void appendRecordToFile(T elt) {
        try {
            Files.writeString(
                    filename,
                    parser.format(elt) + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    getRepositoryName() + " Append File Error", e);
        }
    }
}