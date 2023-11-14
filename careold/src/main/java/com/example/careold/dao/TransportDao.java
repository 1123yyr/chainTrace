package com.example.careold.dao;

import com.example.careold.domain.Produce;
import com.example.careold.domain.Transport;

import java.util.List;

public interface TransportDao {
    int save(Transport transport);
    int update(Transport transport);
    int deleteByAccount(String  account);
    Transport findByAccount(String account);
    List<Transport> findAll();
    int updatePassword(Transport transport);
}
