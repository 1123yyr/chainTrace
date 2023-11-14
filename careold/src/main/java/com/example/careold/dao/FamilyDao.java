package com.example.careold.dao;

import com.example.careold.domain.Family;
import com.example.careold.domain.FamilyDto;
import com.example.careold.domain.FamilyDto2;
import com.example.careold.domain.OldDto2;

import java.util.List;

public interface FamilyDao extends CrudRepository<Family,String>{

    int update2(Family family);

    public List<FamilyDto> findFamily(String s);

    FamilyDto findFamilyByPhone(String phone);

    OldDto2 findOldDto2ByPhone(String phone);

    String findFamilyIdByPhone(String phone);

    FamilyDto2 getFamilyByPhoneForFamily(String phone);

    int updateFamilyTouPic(String pic,String phone);

    int updateFamilyByPhoneForFamily(FamilyDto2 familyDto2);

    int updateFamilyPasswordByPhoneForFamily(String password,String phone);

}
