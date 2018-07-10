package com.trx0eth7.projects.model.entity;

import com.trx0eth7.projects.model.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements IEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic", referencedColumnName = "id")
    private Mechanic mechanic;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "dueDate")
    private Date dueDate;

    @Column(name = "cost")
    private String cost;

    @Column(name = "orderStatus")
    private OrderStatus orderStatus;


    public Order(String description, Customer customer, Mechanic mechanic, Date startDate, Date dueDate, String cost, OrderStatus orderStatus) {
        this.description = description;
        this.customer = customer;
        this.mechanic = mechanic;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.cost = cost;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "description='" + description + '\'' +
                ", customer=" + customer +
                ", mechanic=" + mechanic +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", cost='" + cost + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Order)) return false;

        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getDescription(), order.getDescription()) &&
                Objects.equals(getCustomer(), order.getCustomer()) &&
                Objects.equals(getMechanic(), order.getMechanic()) &&
                Objects.equals(getStartDate(), order.getStartDate()) &&
                Objects.equals(getDueDate(), order.getDueDate()) &&
                Objects.equals(getCost(), order.getCost()) &&
                getOrderStatus() == order.getOrderStatus();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getMechanic() != null ? getMechanic().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + (getOrderStatus() != null ? getOrderStatus().hashCode() : 0);
        return result;
    }
}
