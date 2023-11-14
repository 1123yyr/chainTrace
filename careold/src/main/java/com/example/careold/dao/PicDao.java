package com.example.careold.dao;

import com.example.careold.domain.Pic;

import java.util.List;

public interface PicDao {
    List<Pic> getPic(int picId);

    int addPic(Pic pic);

    int deletePic(int picId);
}
