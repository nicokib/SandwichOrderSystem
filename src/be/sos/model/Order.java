package be.sos.model;

import be.sos.exception.TooManySandwichException;
import be.sos.repository.Identifiable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Order implements Identifiable<Long> {
    private Long id;
    private LocalDate date;
    private Person orderer;
    private Session session;
    private List<SandwichWithOptions> sandwiches;
    private double totalPrice;
    private String remarks;

    public Order(Long id, LocalDate date,Person orderer, Session session, List<SandwichWithOptions> sandwiches, String remarks) {
        Objects.requireNonNull(date, "date cannot be null");
        Objects.requireNonNull(orderer, "orderer cannot be null");
        Objects.requireNonNull(session, "session cannot be null");
        Objects.requireNonNull(sandwiches, "sandwiches cannot be null");

        if(sandwiches.size() > 2) {
            throw new TooManySandwichException("Too many Sandwiches (max 2 sandwiches by order)");
        }

        this.id = id;
        this.date = date;
        this.orderer = orderer;
        this.session = session;
        this.sandwiches = sandwiches;
        this.remarks = remarks;
        calculateTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getOrderer() {
        return orderer;
    }

    public void setOrderer(Person orderer) {
        this.orderer = orderer;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<SandwichWithOptions> getSandwiches() {
        return sandwiches;
    }

    public void setSandwiches(List<SandwichWithOptions> sandwiches) {
        this.sandwiches = sandwiches;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void calculateTotalPrice() {
        totalPrice = sandwiches
                .stream()
                .mapToDouble(SandwichWithOptions::getPrice)
                .sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderer=" + orderer +
                ", session=" + session +
                ", sandwiches=" + sandwiches +
                ", totalPrice=" + totalPrice +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
