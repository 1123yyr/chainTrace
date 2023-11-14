package com.example.careold.dao;

import com.example.careold.domain.Produce;
import com.example.careold.domain.Sale;

import java.util.List;

public interface ProduceDao {

    int save(Produce produce);
    int update(Produce produce);
    int deleteByAccount(String account);
    Produce findByAccount(String account);
    List<Produce>  findAll();
    int updatePassword(Produce produce);

}
