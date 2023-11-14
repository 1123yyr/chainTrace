package com.example.careold.dao;

import com.example.careold.domain.RatepreDto;

import java.util.List;

public interface RatepreDao {

    List<RatepreDto> getRatepre(String name,String timeFirst,String timeLast);

    List<RatepreDto> getRatepreFamily(int old_id, String timeFirst, String timeLast);

    List<RatepreDto> getRatepreDetial(int oldId);

    List<String> getRateCount();

    List<String> getPreCount();
}
