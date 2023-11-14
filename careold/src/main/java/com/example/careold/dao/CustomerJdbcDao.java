package com.example.careold.dao;

import com.example.careold.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerJdbcDao implements CustomerDao{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerJdbcDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CostJdbcDao costJdbcDao;

    @Override
    public Customer findCustomerByPhone(String phone) {
//        String sql="SELECT old_id,name,oldperson.family_id,family_name,family_relation,family_phone,family_sex,family_address,family_tou_pic from oldperson inner JOIN family on family.family_id=oldperson.family_id where family_phone=?";
        String sql="SELECT * from customer  where customer_phone=?";
        Customer customer=jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Customer.class),phone);
        return customer;
    }

    @Override
    public List<Cost> findAllCostByCustomerId(int customerId) {
        String sql1 = "SELECT old_id from oldperson where customer_id=" + customerId;
        List<OldPerson> oldPersons = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<>(OldPerson.class));
        int[] ints = new int[oldPersons.size()];
        for (int i = 0; i < oldPersons.size(); i++) {
            ints[i] = oldPersons.get(i).getOldId();
            System.out.println(ints[i]);
        }
        List<Cost> costs = null;


        for (int i = 0; i < oldPersons.size(); i++) {
            List<Cost> familyselect = costJdbcDao.familyselect(ints[i]);
            System.out.println(familyselect);
            if (!familyselect.isEmpty()) {
                if (costs != null) {
                    System.out.println(familyselect);
                    costs.addAll(familyselect);
                } else {
                    costs = familyselect;
                }
            }
        }
        return costs;
    }

    @Override
    public List<Customer> getCustomer(String customerName) {
        String sql=null;
        List<Customer> customers;
        if(customerName==null || "".equals(customerName)){
            sql="SELECT * FROM customer";
            customers=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Customer.class));
        }else{
            sql="SELECT * FROM customer WHERE customer_name like ?";
            customers=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Customer.class),"%"+customerName+"%");
        }
        return customers;
    }

    @Override
    public int addCustomer(Customer customer) {
        String sql="insert into customer(customer_id,customer_name,customer_age,customer_sex,customer_phone,customer_address) value(?,?,?,?,?,?)";
        int row=jdbcTemplate.update(sql,customer.getCustomerId(),customer.getCustomerName(),customer.getCustomerAge(),customer.getCustomerSex(),customer.getCustomerPhone(),customer.getCustomerAddress());
        return row;
    }

    @Override
    public int updateCustomer(Customer customer) {
        String sql="update customer set customer_name=?,customer_age=?,customer_sex=?,customer_phone=?,customer_address=? where customer_id=?";
        int row=jdbcTemplate.update(sql,customer.getCustomerName(),customer.getCustomerAge(),customer.getCustomerSex(),customer.getCustomerPhone(),customer.getCustomerAddress(),customer.getCustomerId());
        return row;
    }

    @Override
    public int deleteCustomer(int customerId) {
        String sql="delete from customer where customer_id=?";
        int row=jdbcTemplate.update(sql,customerId);
        return row;
    }

    @Override
    public int getMaxId() {
        String sql="SELECT MAX(customer_id) id FROM customer";
        int maxId=jdbcTemplate.queryForObject(sql,int.class);
        return maxId;
    }
}
