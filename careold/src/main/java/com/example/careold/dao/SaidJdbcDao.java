package com.example.careold.dao;

import com.example.careold.domain.Said;
import com.example.careold.domain.Said2;
import com.example.careold.domain.SaidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaidJdbcDao implements SaidDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Said2> getSaid(int textid) {
        String sql="";
        List<Said2> said2s=null;
        if(textid==0){
            sql="SELECT * FROM said ";
            said2s=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Said2.class));
        }else{
            sql="SELECT * FROM said WHERE text_id=?";
            said2s=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Said2.class),textid);
        }
        return said2s;
    }

    @Override
    public List<SaidDto> getSaidForAdmin() {
        String sql="select said_id,said,back,said_time,back_time,person_name,tou_pic,title from said inner join text on said.text_id=text.text_id";
        List<SaidDto> saidDtos=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(SaidDto.class));
        return saidDtos;
    }

    @Override
    public int addSaid(Said said) {
        String sql="INSERT INTO said(said,text_id,said_time,person_name,said_phone,tou_pic) VALUES(?,?,?,?,?,?)";
        int rows=jdbcTemplate.update(sql,said.getSaid(),said.getTextId(),said.getSaidTime(),said.getPersonName(),said.getSaidPhone(),said.getTouPic());
        return rows;
    }

    @Override
    public int deleteSaid(int saidId) {
        String sql="DELETE FROM said WHERE said_id=?";
        int rows=jdbcTemplate.update(sql,saidId);
        return rows;
    }

    @Override
    public int updateSaid(Said said) {
        String sql="UPDATE said SET back=?,back_time=? WHERE said_id=?";
        int rows=jdbcTemplate.update(sql,said.getBack(),said.getBackTime(),said.getSaidId());
        return rows;
    }

    @Override
    public int updateSaid(String saidId,boolean flag) {
        String sql=null;
        if(flag){
            sql="update said set nice_count=nice_count+1 where said_id=?";
        }else{
            sql="update said set nice_count=nice_count-1 where said_id=?";
        }
        int rows=jdbcTemplate.update(sql,saidId);
        return rows;
    }

    @Override
    public List<String> isSaid(int saidId, String phone) {
        String sql="select phone from nice_count where said_id=? and phone=?";
        List<String> phonebic=jdbcTemplate.queryForList(sql,String.class,saidId,phone);
        return phonebic;
    }
}
