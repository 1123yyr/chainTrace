package com.example.careold.dao;

import com.example.careold.domain.Produce;
import com.example.careold.domain.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
@Repository
public class TraceJdbcDao implements TraceDao{

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int produceAdd(Trace trace) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql="insert into trace(pname,paddress,ptime,pperson) VALUES(\'"+trace.getPname()+"\',\'"+trace.getPaddress()+"\',\'"+trace.getPtime()+"\',\'"+trace.getPperson()+"\') ";
        jdbcTemplate.update(new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                // new String[]{"ID"} 确定主键名称
                PreparedStatement ps=conn.prepareStatement(sql, new String[]{"traid"});
                System.out.println(ps);
                return ps;
            }
        }, keyHolder);
        //返回的是主键
        return keyHolder.getKey().intValue();
    }

    @Override
    public int produceUpdate(Trace trace) {
        String sql="UPDATE trace SET pname=?,paddress=?,pperson=?,ptime=? WHERE traid=?";
        int rows=jdbcTemplate.update(sql,trace.getPname(),trace.getPaddress(),trace.getPperson(),trace.getPtime(),trace.getTraid());
        return rows;
    }

    @Override
    public int transportUpdate(Trace trace) {
        String sql="UPDATE trace SET tperson=?,tbeginaddress=?,tarriveaddress=?,tname=? WHERE traid=?";
        int rows=jdbcTemplate.update(sql,trace.getTperson(),trace.getTbeginaddress(),trace.getTarriveaddress(),trace.getTname(),trace.getTraid());
        return rows;
    }

    @Override
    public int saleUpdate(Trace trace) {
        String sql="UPDATE trace SET sperson=?,stime=?,saddress=?,sname=? WHERE traid=?";
        int rows=jdbcTemplate.update(sql,trace.getSperson(),trace.getStime(),trace.getSaddress(),trace.getSname(),trace.getTraid());
        return rows;
    }

    @Override
    public Trace findById(int id) {
        String sql="SELECT * FROM trace WHERE traid=?";
        Trace trace = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Trace.class), id);
        return trace;
    }

    @Override
    public List<Trace> findAll() {
        String sql="SELECT * FROM trace ";
        List<Trace> traces = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Trace.class));
        return traces;
    }
}
