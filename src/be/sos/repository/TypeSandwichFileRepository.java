package be.sos.repository;

import be.sos.enumeration.Language;
import be.sos.model.TypeSandwich;
import be.sos.parser.TypeSandwichParser;
import be.sos.utils.CommonData;

public class TypeSandwichFileRepository
        extends AbstractFileRepository<TypeSandwich, Integer>
        implements TypeSandwichRepository {

    private static final String FILENAME = CommonData.getPathData() +"/typeSandwiches.csv";
    private static final String HEADER = "id;names";

    private static final TypeSandwichParser parser = new TypeSandwichParser();

    private static final TypeSandwichFileRepository INSTANCE =
            new TypeSandwichFileRepository();

    public static TypeSandwichFileRepository getInstance() {
        return INSTANCE;
    }

    private TypeSandwichFileRepository() {
        super(FILENAME, HEADER, parser,0);
    }

    @Override
    protected Integer incrementId(Integer id) {
        return id + 1;
    }

    @Override
    protected String getRepositoryName() {
        return "TypeSandwich";
    }


    // getAll(), find(), add(), delete() already define in super class

    @Override
    public void update(TypeSandwich elt) {
        if(elt.getId() == null) throw new NullPointerException("Update TypeSandwich error : the id cannot be null");
        TypeSandwich typeSandwichToUpdate = findById(elt.getId()).orElseThrow(() -> new IllegalArgumentException("Update TypeSandwich error : TypeSandwich with id " + elt.getId() + " not found"));
        for(Language lang : Language.values())
            if(elements.stream().anyMatch(i -> !i.getId().equals(elt.getId()) && i.getName(lang).equals(elt.getName(lang)))) throw new IllegalArgumentException("Update TypeSandwich error : TypeSandwich name " + lang.getLabel() + " already exists be unique");

        typeSandwichToUpdate.getNames().clear();
        if (elt.getNames() != null) {
            typeSandwichToUpdate.getNames().putAll(elt.getNames());
        }
        writeAllFile();
    }

}