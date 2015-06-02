package com.websystique.spring;

import java.math.BigDecimal;
import java.util.List;

import com.websystique.spring.model.Address;
import com.websystique.spring.model.Phone;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.websystique.spring.model.Employee;
import com.websystique.spring.service.EmployeeService;

/**
 * Test with app context xml and props
 *
 */
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestApp {
    @Autowired
    EmployeeService service;

    @Test
    public void testHibernate() throws Exception {
        /*
         * Create Employee1
         */
        Employee employee1 = new Employee();
        employee1.setName("Han Yenn");
        employee1.setJoiningDate(new LocalDate(2010, 10, 10));
        employee1.setSalary(new BigDecimal(10000));
        employee1.setSsn("ssn00000001");
        service.saveEmployee(employee1); //TODO had to move this up for phones

        Address address1 = new Address("street1", "city1", "state1", "zipcode1");
        employee1.setAddress(address1);
        Phone phone1 = new Phone("model1", "manu1", 11111);
        phone1.setEmployee(employee1);
        employee1.getPhones().add(phone1);
        //service.savePhone   TODO


        /*
         * Create Employee2
         */
        Employee employee2 = new Employee();
        employee2.setName("Dan Thomas");
        employee2.setJoiningDate(new LocalDate(2012, 11, 11));
        employee2.setSalary(new BigDecimal(20000));
        employee2.setSsn("ssn00000002");
        service.saveEmployee(employee2);

        Address address2 = new Address("street2", "city2", "state2", "zipcode2");
        employee2.setAddress(address2);

        Phone phone2 = new Phone("model2", "manu2", 22222);
        phone2.setEmployee(employee2);
        employee2.getPhones().add(phone2);

        /*
         * Persist both Employees
         */

        /*
         * Get all employees list from database
         */
        List<Employee> employees = service.findAllEmployees();
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        /*
         * delete an employee
         */
        service.deleteEmployeeBySsn("ssn00000002");

        /*
         * update an employee
         */

        Employee employee = service.findBySsn("ssn00000001");
        employee.setSalary(new BigDecimal(50000));
        service.updateEmployee(employee);

        /*
         * Get all employees list from database
         */
        List<Employee> employeeList = service.findAllEmployees();
        for (Employee emp : employeeList) {
            System.out.println(emp);
        }

        // Thread.sleep(300 * 1000); //allow time to look at results in DB
    }
}
