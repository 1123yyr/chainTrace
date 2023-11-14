package com.example.careold.dao;

import com.example.careold.domain.Cost;
import com.example.careold.domain.Customer;



import java.util.List;

public interface CustomerDao {
    List<Customer> getCustomer(String customerName);

    int addCustomer(Customer customer);

    int updateCustomer(Customer customer);

    int deleteCustomer(int customerId);

    int getMaxId();

    Customer findCustomerByPhone(String phone) ;

    List<Cost> findAllCostByCustomerId(int customerId);


}
