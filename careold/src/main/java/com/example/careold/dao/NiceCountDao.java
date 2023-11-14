package com.example.careold.dao;

import java.util.List;

public interface NiceCountDao {

    List<String> getCount(int saidId, String phone);

    int deleteCount(int saidId,String phone);

    int addCount(int saidId,String phone);
}
