package com.trx0eth7.projects.model.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mechanics")
public class Mechanic implements IEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "mechanic", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Order> orders = new ArrayList<>();

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "fatherName")
    private String fatherName;

    @Column(name = "payPerHour")
    private Integer payPerHour;

    public Mechanic() {
    }

    public Mechanic(String firstName, String lastName, String fatherName, Integer payPerHour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.payPerHour = payPerHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Integer getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(Integer payPerHour) {
        this.payPerHour = payPerHour;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Mechanic)) return false;

        Mechanic mechanic = (Mechanic) o;
        return Objects.equals(getId(), mechanic.getId()) &&
                Objects.equals(getFirstName(), mechanic.getFirstName()) &&
                Objects.equals(getLastName(), mechanic.getLastName()) &&
                Objects.equals(getFatherName(), mechanic.getFatherName()) &&
                Objects.equals(getPayPerHour(), mechanic.getPayPerHour());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getFatherName() != null ? getFatherName().hashCode() : 0);
        result = 31 * result + (getPayPerHour() != null ? getPayPerHour().hashCode() : 0);
        return result;
    }
}
