package com.example.careold.dao;

import com.example.careold.domain.Produce;
import com.example.careold.domain.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TransportJdbcDao implements TransportDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int save(Transport transport) {
        String sql="insert into transport(taccount,tpassword,tname,temail,ttime) VALUES(?,?,?,?,?)";
        int row= jdbcTemplate.update(sql,transport.getTaccount(),"123456",transport.getTname(),transport.getTemail(),transport.getTtime());
        return row;
    }

    @Override
    public int update(Transport transport) {
        String sql="UPDATE transport SET tname=?,temail=?,ttime=? WHERE taccount=?";
        int rows=jdbcTemplate.update(sql,transport.getTname(),transport.getTemail(),transport.getTtime(),transport.getTaccount());
        return rows;
    }

    @Override
    public int deleteByAccount(String account) {
        String sql="DELETE FROM transport WHERE taccount=?";
        int row =jdbcTemplate.update(sql,account);
        return row;
    }


    @Override
    public Transport findByAccount(String account) {
        String sql="SELECT * FROM transport WHERE taccount=?";
        Transport transport = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Transport.class), account);
        return transport;
    }

    @Override
    public List<Transport> findAll() {
        String sql="SELECT * FROM transport ";
        List<Transport> transports = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transport.class));
        return transports;
    }

    @Override
    public int updatePassword(Transport transport) {
        String sql="UPDATE transport SET tpassword=? WHERE taccount=?";
        int rows=jdbcTemplate.update(sql,transport.getTpassword(),transport.getTaccount());
        return rows;
    }


}
