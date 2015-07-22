package com.rooney.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * http://spring.io/guides/gs/relational-data-access/<br>
 * https://github.com/spring-guides
 */
@ContextConfiguration(locations = { "classpath:JdbcTemplateTest-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcTemplateTest {
    private static final String TABLE = "CUSTOMER";
    private static final String SEQUENCE = "CUSTOMER_SEQ";
    private static final String SEQUENCE_SQL =  "SELECT " + SEQUENCE + ".nextval from dual";
    private static final String SELECT_ALL_SQL = "select * from " +  TABLE;
    private static final String SELECT_BY_ID_SQL = SELECT_ALL_SQL + " where id = ?";
    CustomerRowMapper rowMapper = new CustomerRowMapper();
    BeanPropertyRowMapper<Customer> beanRowMapper = new BeanPropertyRowMapper<Customer>(Customer.class);
            
            
//	@Autowired
	JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;
    private CustomerMappingQuery customerMappingQuery;
	
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insert = new SimpleJdbcInsert(dataSource).withTableName(TABLE);
        this.customerMappingQuery = new CustomerMappingQuery(dataSource);
    }
	

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

    public Long generatePrimaryKey() {
        return jdbcTemplate.queryForObject(SEQUENCE_SQL, Long.class);
    }
    
    public void createCustomer(Customer customer) {
        customer.setId(generatePrimaryKey());
        insert.execute(new BeanPropertySqlParameterSource(customer));
    }    
    
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_SQL, beanRowMapper);
    }
    
    public Customer findById(long id) {
//        return rcrMappingQuery.findObject(id);
//        return jdbcTemplate.queryForObject(SELECT_ALL_SQL, new Object[] { id }, rowMapper);         
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[] { id }, beanRowMapper);         
        
    }    
    
    
    public class CustomerMappingQuery extends MappingSqlQuery<Customer> {
        public CustomerMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        protected Customer mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return rowMapper.mapRow(rs, rowNumber);
        }
    }     
    
    public class CustomerRowMapper implements RowMapper<Customer> {

        public Customer mapRow(ResultSet rs, int rowNumber) throws SQLException {
            Customer cust = new Customer();
            cust.setId(rs.getLong("id")); 
            //, rs.getString("first_name"), rs.getString("last_name"));
            return cust;
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

        public Customer() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public String toString() {
            return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
        }
        // getters & setters omitted for brevity

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
    }

}
