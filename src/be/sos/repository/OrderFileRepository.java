package be.sos.repository;

import be.sos.model.Order;
import be.sos.parser.OrderParser;
import be.sos.utils.CommonData;

public class OrderFileRepository
        extends AbstractFileRepository<Order, Long>
        implements OrderRepository {

    private static final String FILENAME = CommonData.getPathData() + "/orders.ndjson";
    //private static final String HEADER = "id;date;ordererId;ordererRole;firstname;lastname;language;email;sessionId;courseTitle;startDate;endDate;location;totalPrice;remarks;nbrSandwich;sandwichId1;sandwichTypeFr1;sandwichTypeNl1;sandwichNamefr1;sandwichNameNl1;crudities1;breadType1;price1;sandwichId2;sandwichType2;sandwichName2;crudities2;breadType2;price2";
    private static final String HEADER = null;

    private static final OrderParser parser = new OrderParser();

    private static final OrderFileRepository INSTANCE =
            new OrderFileRepository();

    public static OrderFileRepository getInstance() {
        return INSTANCE;
    }

    private OrderFileRepository() {
        super(FILENAME, HEADER, parser,0L);
    }

    @Override
    protected Long incrementId(Long id) {
        return id + 1;
    }

    @Override
    protected String getRepositoryName() {
        return "Order";
    }

    // getAll(), find(), add(), delete() already define in super class

    @Override
    public void update(Order elt) {

        if (elt.getId() == null) {
            throw new NullPointerException("Update Order error : id cannot be null");
        }

        Order orderToUpdate = findById(elt.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order with id " + elt.getId() + " not found"));

        if (elements.stream().anyMatch(c ->
                !c.getId().equals(elt.getId()) && c.equals(elt))) {
            throw new IllegalArgumentException(
                    "This order already exists");
        }

        // Update properties
        orderToUpdate.setOrderer(elt.getOrderer());
        orderToUpdate.setSession(elt.getSession());
        orderToUpdate.setSandwiches(elt.getSandwiches());
        orderToUpdate.setTotalPrice(elt.getTotalPrice());
        orderToUpdate.setRemarks(elt.getRemarks());
        writeAllFile();
    }
}