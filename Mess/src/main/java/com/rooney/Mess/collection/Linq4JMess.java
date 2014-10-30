package com.rooney.Mess.collection;

import net.hydromatic.linq4j.Linq4j;
import net.hydromatic.linq4j.function.Function0;
import net.hydromatic.linq4j.function.Function1;
import net.hydromatic.linq4j.function.Function2;
import net.hydromatic.linq4j.function.Functions;

public class Linq4JMess {
	public static void main(String[] args) {
	    String s = Linq4j.asEnumerable(emps)
	            .groupBy(
	                EMP_DEPTNO_SELECTOR,
	                new Function0<String>() {
	                  public String apply() {
	                    return null;
	                  }
	                },
	                new Function2<String, Employee, String>() {
	                  public String apply(String v1, Employee e0) {
	                    return v1 == null ? e0.name : (v1 + "+" + e0.name);
	                  }
	                },
	                new Function2<Integer, String, String>() {
	                  public String apply(Integer v1, String v2) {
	                    return v1 + ": " + v2;
	                  }
	                })
	            .orderBy(Functions.<String>identitySelector())
	            .toList()
	            .toString();
	        assert s.equals("[10: Fred+Eric+Janet, 30: Bill]");
	}
	
	public static class Employee {
	    public final int empno;
	    public final String name;
	    public final int deptno;

	    public Employee(int empno, String name, int deptno) {
	      this.empno = empno;
	      this.name = name;
	      this.deptno = deptno;
	    }

	    public String toString() {
	      return "Employee(name: " + name + ", deptno:" + deptno + ")";
	    }
	  }

	  public static final Employee[] emps = {
	      new Employee(100, "Fred", 10),
	      new Employee(110, "Bill", 30),
	      new Employee(120, "Eric", 10),
	      new Employee(130, "Janet", 10),
	  };

	  public static final Function1<Employee, Integer> EMP_DEPTNO_SELECTOR =
	      new Function1<Employee, Integer>() {
	        public Integer apply(Employee employee) {
	          return employee.deptno;
	        }
	      };	
}
