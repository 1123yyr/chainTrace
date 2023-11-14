package com.example.careold.dao;

import com.example.careold.domain.Cost;
import com.example.careold.domain.FamilyDto;

import java.util.List;

public interface CostDao {

    int save (Cost cost);

    List<Cost> findFamily(String s);

    int update(Cost cost);

    int deleted(int id);

    List<Cost> familyselect(int id);

    Cost findFamilyByorderid(int orderid);


}
