package com.websystique.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * http://www.dzone.com/tutorials/java/hibernate/hibernate-example/hibernate-mapping-many-to-one-using-annotations-1.html
 */
@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private Long id;

    @Column(name = "ADDRESS_STREET", nullable = false, length=250)
    private String street;

    @Column(name = "ADDRESS_CITY", nullable = false, length=50)
    private String city;

    @Column(name = "ADDRESS_STATE", nullable = false, length=50)
    private String state;

    @Column(name = "ADDRESS_ZIPCODE", nullable = false, length=10)
    private String zipcode;

    //http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html#tutorial-associations-unidirset
    public Address() {
    }

    public Address(String street, String city, String state, String zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

//not needed http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html#tutorial-associations-unidirset
//    public void setAddressId(long addressId) {
//        this.addressId = addressId;
//    }


    public Long getId() {
        return this.id;
    }


    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null) return false;
        } else if (!city.equals(other.city)) return false;
        if (state == null) {
            if (other.state != null) return false;
        } else if (!state.equals(other.state)) return false;
        if (street == null) {
            if (other.street != null) return false;
        } else if (!street.equals(other.street)) return false;
        if (zipcode == null) {
            if (other.zipcode != null) return false;
        } else if (!zipcode.equals(other.zipcode)) return false;
        return true;
    }

    @Override public String toString() {
        return "Address [id=" + id + ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + "]";
    }


}
