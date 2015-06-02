package com.websystique.spring.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;


/**
 * TODO annotate fields or methods?
 */
@Entity
@Table(name="EMPLOYEE")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "JOINING_DATE", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate joiningDate;

	@Column(name = "SALARY", nullable = false)
	private BigDecimal salary;

	@Column(name = "SSN", unique=true, nullable = false)
	private String ssn;

    @ManyToOne(cascade = CascadeType.ALL, optional = false) //'optional' controls inner vs left-outer join
    private Address address;

    //TODO how to avoid LOJ on eager fetch
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //using 'eager' not 'lazy' as entity is passed back outside Transactional boundary.
    @JoinColumn(name="EMPLOYEE_ID", nullable = false) //without this a JOIN Table is created/used. default name is PHONES_ID. 'nullable = false' does not get rid of LOJs 
    private Set<Phone> phones = new HashSet<Phone>(); //appears to be needed

    
    //http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html#tutorial-associations-unidirset
    public Employee() {
        super();
    }

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //using 'eager' not 'lazy' as entity is passed back outside Transactional boundary.
//    @JoinColumn(name="EMPLOYEE_ID") //without this a JOIN Table is created/used. default name is PHONES_ID
    public Set<Phone> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	//not needed http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch01.html#tutorial-associations-unidirset
    // but needed if annotate methods
//	public void setId(Long id) {
//		this.id = id;
//	}

//    @Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//    @Column(name = "JOINING_DATE", nullable = false)
//    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

//	@Column(name = "SALARY", nullable = false)
	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

//	@Column(name = "SSN", unique=true, nullable = false)
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
//
//	@ManyToOne(cascade = CascadeType.ALL)
	public Address getAddress() {
		return this.address;
	}

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((joiningDate == null) ? 0 : joiningDate.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((salary == null) ? 0 : salary.hashCode());
        result = prime * result + ((ssn == null) ? 0 : ssn.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Employee other = (Employee) obj;
        if (joiningDate == null) {
            if (other.joiningDate != null) return false;
        } else if (!joiningDate.equals(other.joiningDate)) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (salary == null) {
            if (other.salary != null) return false;
        } else if (!salary.equals(other.salary)) return false;
        if (ssn == null) {
            if (other.ssn != null) return false;
        } else if (!ssn.equals(other.ssn)) return false;
        return true;
    }

    @Override public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", joiningDate=" + joiningDate + ", salary=" + salary + ", ssn=" + ssn + ", address=" + address + ", phones=" + phones
                + "]";
    }
}
