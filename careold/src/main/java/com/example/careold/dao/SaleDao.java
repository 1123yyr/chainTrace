package com.example.careold.dao;

import com.example.careold.domain.Sale;

import java.util.List;

public interface SaleDao {

    int save(Sale sale);
    int update(Sale sale);
    int deleteByAccount(String account);
    Sale findByAccount(String account);
    List<Sale> findAll();
    int updatePassword(Sale sale);

}
