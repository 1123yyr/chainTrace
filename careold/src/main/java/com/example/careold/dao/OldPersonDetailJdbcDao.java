package com.example.careold.dao;

import com.example.careold.domain.OldPerson;
import com.example.careold.domain.OldPersonDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OldPersonDetailJdbcDao implements OldPersonDetailDao{


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<OldPersonDetail> getOldPersonDetail(String name) {
        String sql="";
        List<OldPersonDetail> oldPersonDetail=null;
        if(name==null || "".equals(name)){
            sql="select * from oldperson LEFT JOIN family on oldperson.family_id=family.family_id LEFT JOIN customer on oldperson.customer_id=customer.customer_id";
            oldPersonDetail=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(OldPersonDetail.class));
        }else{
            sql="select * from oldperson LEFT JOIN family on oldperson.family_id=family.family_id LEFT JOIN customer on oldperson.customer_id=customer.customer_id where name like ?";
            oldPersonDetail=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(OldPersonDetail.class),"%"+name+"%");
        }
        return oldPersonDetail;
    }
}
