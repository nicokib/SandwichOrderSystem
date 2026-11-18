package be.sos.repository;

import be.sos.enumeration.Language;
import be.sos.model.Sandwich;
import be.sos.parser.SandwichParser;
import be.sos.utils.CommonData;

public class SandwichFileRepository
        extends AbstractFileRepository<Sandwich, Integer>
        implements SandwichRepository {

    private static final String FILENAME = CommonData.getPathData() +"/sandwiches.csv";
    private static final String HEADER = "id;names;typeSandwich;price;cruditiesOptionAvailable;ingredients";

    private static final SandwichParser parser = new SandwichParser();

    private static final SandwichFileRepository INSTANCE =
            new SandwichFileRepository();

    public static SandwichFileRepository getInstance() {
        return INSTANCE;
    }

    private SandwichFileRepository() {
        super(FILENAME, HEADER, parser,0);
    }

    @Override
    protected Integer incrementId(Integer id) {
        return id + 1;
    }

    @Override
    protected String getRepositoryName() {
        return "Sandwich";
    }

    // getAll(), find(), add(), delete() already define in super class

    @Override
    public void update(Sandwich elt) {
        if(elt.getId() == null) throw new NullPointerException("Update Sandwich error : the id cannot be null");
        Sandwich sandwichToUpdate = findById(elt.getId()).orElseThrow(() -> new IllegalArgumentException("Update Sandwich error : Sandwich with id " + elt.getId() + " not found"));

        for(Language lang : Language.values())
            if(elements.stream().anyMatch(i -> !i.getId().equals(elt.getId()) && i.getName(lang).equals(elt.getName(lang)))) throw new IllegalArgumentException("Update Sandwich error : Sandwich name " + lang.getLabel() + " already exists be unique");

        sandwichToUpdate.getNames().clear();
        if (elt.getNames() != null) {
            sandwichToUpdate.getNames().putAll(elt.getNames());
        }
    }

}