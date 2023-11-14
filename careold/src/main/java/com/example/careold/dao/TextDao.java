package com.example.careold.dao;

import com.example.careold.domain.Text;

import java.util.List;

public interface TextDao {
    List<Text> selectTextAll(String title);

    Text selectTextDetail(int textId);

    int addText(Text text);

    int deleteText(int id);

    int update(Text text);

    int getMaxPicId();
}
