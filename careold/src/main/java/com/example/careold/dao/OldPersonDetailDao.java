package com.example.careold.dao;

import com.example.careold.domain.OldPersonDetail;

import java.util.List;

public interface OldPersonDetailDao {
    List<OldPersonDetail> getOldPersonDetail(String name);
}
