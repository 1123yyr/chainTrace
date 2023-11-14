package com.example.careold.dao;

import com.example.careold.domain.Trace;

import java.util.List;

public interface TraceDao {

    int produceAdd(Trace trace);
    int produceUpdate(Trace trace);
    int transportUpdate(Trace trace);
    int saleUpdate(Trace trace);
    Trace findById(int id);
    List<Trace> findAll();
}
