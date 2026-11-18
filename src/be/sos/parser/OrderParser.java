package be.sos.parser;

import be.sos.enumeration.Language;
import be.sos.enumeration.PersonRole;
import be.sos.model.*;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderParser implements Parser<Order> {

    static private Gson gson = new Gson();

    private class PersonSimple {
        public Integer id;
        public PersonRole role;
        public String firstName;
        public String lastName;
        public Language language;
        public String email;

        public PersonSimple(Person person) {
            this.id = person.getId();
            this.role = person.getRole();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.language = person.getLanguage();
            this.email = person.getEmail();
        }

        public Person generatePerson() {
            return new Person(id, role, firstName, lastName, language, email, null);
        }
    }

    public class SessionSimple {
        public Long id;
        public Course course;
        public LocalDate startDate;
        public LocalDate endDate;
        public String location;
        public PersonSimple instructor;

        public SessionSimple(Session session) {
            this.id = session.getId();
            this.course = session.getCourse();
            this.startDate = session.getStartDate();
            this.endDate = session.getEndDate();
            this.location = session.getLocation();
            this.instructor = new PersonSimple(session.getInstructor());
        }

        public Session generateSession() {
            return new Session(id, course, startDate, endDate, location, instructor.generatePerson(), null);
        }
    }

    private class OrderRecord {
        public Long id;
        public LocalDate date;
        public PersonSimple orderer;
        public SessionSimple session;
        public List<SandwichWithOptions> sandwiches;
        public double totalPrice;
        public String remarks;

        public OrderRecord(Order order) {
            this.id = order.getId();
            this.date = order.getDate();
            this.orderer = new PersonSimple(order.getOrderer());
            this.session = new SessionSimple(order.getSession());
            this.sandwiches = order.getSandwiches();
            this.totalPrice = order.getTotalPrice();
            this.remarks = order.getRemarks();
        }

        public Order generateOrder() {
            return new Order(id, date, orderer.generatePerson(), session.generateSession(), sandwiches, remarks);
        }
    }


    // csv format :id;date;ordererId;ordererRole;firstname;lastname;language;email;sessionId;courseTitle;startDate;endDate;location;totalPrice;remarks;nbrSandwich;sandwichId1;sandwichTypeFr1;sandwichTypeNl1;sandwichNamefr1;sandwichNameNl1;crudities1;breadType1;price1;sandwichId2;sandwichType2;sandwichName2;crudities2;breadType2;price2
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");

    @Override
    public Order parse(String line)  {
        OrderRecord orderRecord = gson.fromJson(line, OrderRecord.class);
        return orderRecord.generateOrder();
    }

    @Override
    public String format(Order o) {
        OrderRecord orderRecord = new OrderRecord(o);
        return gson.toJson(orderRecord);
    }
}
