package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends AbstractDao implements EmployeeDao{

	public void saveEmployee(Employee employee) {
		persist(employee);
	}

	@SuppressWarnings("unchecked")
	public List<Employee> findAllEmployees() {
		Criteria criteria = getSession().createCriteria(Employee.class);
		return (List<Employee>) criteria.list();
	}

	public void deleteEmployeeBySsn(String ssn) {
	    //BULK SQL - doesn't work when have child entities
//		Query query = getSession().createSQLQuery("delete from Employee where ssn = :ssn");
//		query.setString("ssn", ssn);
//		query.executeUpdate();
	    
	    //Retrieve each, and deleting explicitly and thus getting CASCADE delete for free
	    Criteria criteria = getSession().createCriteria(Employee.class);
	    criteria.add(Restrictions.eq("ssn", ssn));
	    
	    for (Object employee : criteria.list()) {
	        getSession().delete(employee);
        }
	}

	
	public Employee findBySsn(String ssn){
		Criteria criteria = getSession().createCriteria(Employee.class);
		criteria.add(Restrictions.eq("ssn",ssn));
		return (Employee) criteria.uniqueResult();
	}
	
	public void updateEmployee(Employee employee){
		getSession().update(employee);
	}
	
}
