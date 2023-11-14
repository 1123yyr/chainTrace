package com.example.careold.dao;

import com.example.careold.domain.Said;
import com.example.careold.domain.Said2;
import com.example.careold.domain.SaidDto;

import java.util.List;

public interface SaidDao {
    List<Said2> getSaid(int textid);

    List<SaidDto> getSaidForAdmin();

    int addSaid(Said said);

    int deleteSaid(int saidId);

    int updateSaid(Said said);

    List<String> isSaid(int saidId,String phone);

    int updateSaid(String saidId,boolean flag);
}
