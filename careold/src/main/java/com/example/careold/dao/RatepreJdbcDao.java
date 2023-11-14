package com.example.careold.dao;

import com.example.careold.domain.RatepreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RatepreJdbcDao implements RatepreDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<RatepreDto> getRatepre(String name, String timeFirst, String timeLast) {
        String sql;
        List<RatepreDto> ratepreDtos=null;
        if((name==null || "".equals(name)) && (timeFirst==null || "".equals(timeFirst)) && (timeFirst==null || "".equals(timeFirst))){
            sql="SELECT oldperson.`name`,oldperson.old_id,oldperson.`sex`,rate_state,pre_state,ratepre.`create_time` FROM oldperson INNER JOIN watch_info ON oldperson.device_id=watch_info.device_id INNER JOIN ratepre ON watch_info.`device_id`=ratepre.`device_id`  GROUP BY oldperson.old_id  ORDER BY ratepre.create_time DESC";
            ratepreDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RatepreDto.class));
        }else if(name==null || "".equals(name)){
            sql="SELECT oldperson.`name`,oldperson.old_id,oldperson.`sex`,rate_state,pre_state,ratepre.`create_time` FROM oldperson INNER JOIN watch_info ON oldperson.device_id=watch_info.device_id INNER JOIN ratepre ON watch_info.`device_id`=ratepre.`device_id` WHERE create_time BETWEEN ? AND ? GROUP BY oldperson.old_id  ORDER BY ratepre.create_time DESC";
            ratepreDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RatepreDto.class),timeFirst,timeLast);
        }else if((timeFirst==null || "".equals(timeFirst)) && (timeFirst==null || "".equals(timeFirst))){
            sql="SELECT oldperson.`name`,oldperson.old_id,oldperson.`sex`,rate_state,pre_state,ratepre.`create_time` FROM oldperson INNER JOIN watch_info ON oldperson.device_id=watch_info.device_id INNER JOIN ratepre ON watch_info.`device_id`=ratepre.`device_id` WHERE oldperson.name like ? GROUP BY oldperson.old_id  ORDER BY ratepre.create_time DESC";
            ratepreDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RatepreDto.class),"%"+name+"%");
        }
        return ratepreDtos;
    }

    @Override
    public List<RatepreDto> getRatepreFamily(int oldId, String timeFirst, String timeLast) {
        String sql;
        List<RatepreDto> ratepreDtos=null;
        if((timeFirst==null || "".equals(timeFirst)) && (timeFirst==null || "".equals(timeFirst))){
            sql="SELECT oldperson.`name`,oldperson.old_id,oldperson.`sex`,rate_state,pre_state,ratepre.`create_time` FROM oldperson INNER JOIN watch_info ON oldperson.device_id=watch_info.device_id INNER JOIN ratepre ON watch_info.`device_id`=ratepre.`device_id` WHERE oldperson.old_id=? GROUP BY oldperson.old_id  ORDER BY ratepre.create_time DESC";
            ratepreDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RatepreDto.class),oldId);
        }else{
            sql="SELECT oldperson.`name`,oldperson.old_id,oldperson.`sex`,rate_state,pre_state,ratepre.`create_time` FROM oldperson INNER JOIN watch_info ON oldperson.device_id=watch_info.device_id INNER JOIN ratepre ON watch_info.`device_id`=ratepre.`device_id` WHERE create_time BETWEEN ? AND ? and oldperson.old_id=? GROUP BY oldperson.old_id  ORDER BY ratepre.create_time DESC";
            ratepreDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RatepreDto.class),timeFirst,timeLast,oldId);
        }
        return ratepreDtos;
    }

    @Override
    public List<RatepreDto> getRatepreDetial(int oldId) {
        String sql="SELECT hr,bp,ratepre.`create_time` FROM oldperson INNER JOIN watch_info ON oldperson.device_id=watch_info.device_id INNER JOIN ratepre ON watch_info.`device_id`=ratepre.`device_id` WHERE oldperson.old_id=? ORDER BY ratepre.create_time ASC LIMIT 30";
        List<RatepreDto> ratepreDtos=null;
        ratepreDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RatepreDto.class),oldId);
        return ratepreDtos;
    }

    @Override
    public List<String> getRateCount() {
        String sql="SELECT device_id deviceId FROM ratepre WHERE rate_state='1' GROUP BY device_id";
        List<String> deviceids=null;
        deviceids=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(String.class));
        return deviceids;
    }

    @Override
    public List<String> getPreCount() {
        String sql="SELECT device_id deviceId FROM ratepre WHERE pre_state='1' GROUP BY device_id";
        List<String> deviceids=null;
        deviceids=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(String.class));
        return deviceids;
    }


}
