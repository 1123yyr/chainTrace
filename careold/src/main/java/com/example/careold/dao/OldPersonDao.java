package com.example.careold.dao;

import com.example.careold.domain.OldPerson;

import java.util.List;

public interface OldPersonDao {
    List<OldPerson> getOldPerson(String name);

    int addOldPerson(OldPerson oldPerson);

    int updateOldPerson(OldPerson oldPerson);

    int deleteOldPerson(int oldPersonId);

    int updateOldPerson2(int oldId, int familyId);

    List<OldPerson> selectfamilylife(int id);
}
