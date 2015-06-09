package com.rooney.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * http://spring.io/guides/gs/relational-data-access/<br>
 * https://github.com/spring-guides
 */
@ContextConfiguration(locations = { "classpath:JdbcTemplateTest-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcTemplateTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

    @Test
	public void testBasicJDBCTemplate() {
        System.out.println("Creating tables");
        jdbcTemplate.execute("drop table customers if exists");
        jdbcTemplate.execute("create table customers(" + "id serial, first_name varchar(255), last_name varchar(255))");

        String[] fullNames = new String[] { "John Woo", "Jeff Dean", "Josh Bloch", "Josh Long" };
        for (String fullname : fullNames) {
            String[] name = fullname.split(" ");
            System.out.printf("Inserting customer record for %s %s\n", name[0], name[1]);
            jdbcTemplate.update("INSERT INTO customers(first_name,last_name) values(?,?)", name[0], name[1]);
        }

        System.out.println("Querying for customer records where first_name = 'Josh':");
        List<Customer> results = jdbcTemplate.query("select id, first_name, last_name from customers where first_name = ?", new Object[] { "Josh" }, new RowMapper<Customer>() {
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"));
            }
        });

        for (Customer customer : results) {
            System.out.println(customer);
        }
	}

//TODO test using oracle sequence (maybe h2 in oracle mode)
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//    getJdbcTemplate().update(
//        new PreparedStatementCreator() {
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {ID});
//                ps.setString(1, rcr.getDescription());
//                return ps;
//            }
//        },
//        keyHolder);
//
//    rcr.setId(keyHolder.getKey().longValue());
//    return keyHolder.getKey().longValue();         
//OR
//  setup() {    
    //  this.insert = new SimpleJdbcInsert(dataSource)  
    //  .withTableName(TABLE_NAME)  
    //  .usingGeneratedKeyColumns(ID);  
//}
    //TODO Test https://jira.spring.io/browse/SPR-3492 use jdbcTemplate.getJdbcOperations.update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) or use the new SimpleJdbcInsert  //
    //no good way to provide seq.nextval
//    Map<String, Object> parameters = new HashMap<String, Object>();  
//    parameters.put("DESCRIPTION", obj.getDescription());
//    Long rcrId = insert.executeAndReturnKey(parameters).longValue();
//    rcr.setId(rcrId);
//    return rcrId;
        
    
    
    public class Customer {
        private long id;
        private String firstName, lastName;

        public Customer(long id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
        }
        // getters & setters omitted for brevity
    }

}
