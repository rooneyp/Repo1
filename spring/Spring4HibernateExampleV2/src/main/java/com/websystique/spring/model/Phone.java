package com.websystique.spring.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by paul on 01/06/15.
 */
@Entity
@Table 
public class Phone {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private String model;
    @Column
    private String manufacturer;
    @Column
    private int number;

//    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
//    private Employee employee;

    //http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html#tutorial-associations-unidirset
    public Phone() {
        super();
    }

    public Phone(String model, String manufacturer, int number) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

//not needed http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html#tutorial-associations-unidirset    
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//    
    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + number;
        return result;
    }
    
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Phone other = (Phone) obj;
        if (manufacturer == null) {
            if (other.manufacturer != null) return false;
        } else if (!manufacturer.equals(other.manufacturer)) return false;
        if (model == null) {
            if (other.model != null) return false;
        } else if (!model.equals(other.model)) return false;
        if (number != other.number) return false;
        return true;
    }
    
    @Override public String toString() {
        return "Phone [id=" + id + ", model=" + model + ", manufacturer=" + manufacturer + ", number=" + number + "]";
    }
}
