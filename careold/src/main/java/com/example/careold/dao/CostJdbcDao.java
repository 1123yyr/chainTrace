package com.example.careold.dao;

import com.example.careold.domain.Cost;
import com.example.careold.domain.Family;
import com.example.careold.domain.FamilyDto;
import com.example.careold.domain.OldPerson;
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
public class CostJdbcDao implements CostDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Cost cost) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql="insert into cost(oldid,thing,time,money,name,orderstatus) VALUES("+cost.getOldid()+",\'"+cost.getThing()+"\',\'"+cost.getTime()+"\',"+cost.getMoney()+",\'"+cost.getName()+"\',\'"+cost.getOrderstatus()+"\') ";
        jdbcTemplate.update(new PreparedStatementCreator(){
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                // new String[]{"ID"} 确定主键名称
                PreparedStatement ps=conn.prepareStatement(sql, new String[]{"orderid"});
                System.out.println(ps);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();

    }


    @Override
    public List<Cost> findFamily(String s) {
        String sql="";
        List<Cost> Costs=null;
        if(s==null || "".equals(s)){
            sql="select oldid,thing,time,money,name,orderid , orderstatus from cost";
            Costs=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Cost.class));
            System.out.println("-----------");
        }else{
            sql="select oldid,thing,time,money,name,orderid ,orderstatus from cost where name like ? ";
            Costs=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Cost.class),"%"+s+"%");

        }
        return Costs;
    }

    @Override
    public int update(Cost cost) {
        String sql="UPDATE cost SET thing=?,time=?,money=?,name=? ,oldid=?,orderstatus=? WHERE orderid=?";
        int rows=jdbcTemplate.update(sql,cost.getThing(),cost.getTime(),cost.getMoney(),cost.getName(),cost.getOldid(),cost.getOrderstatus(),cost.getOrderid());
        return rows;
    }

    @Override
    public int deleted(int id) {
        String sql="DELETE FROM cost WHERE orderid=?";
        int rows=jdbcTemplate.update(sql,id);
        return rows;
    }

    //按照老人查询订单
    @Override
    public List<Cost> familyselect(int id) {
        String sql = "select * from cost where oldid like ?";
        List<Cost> costs=null;
//        int rows = jdbcTemplate.update(sql,id);
        costs=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Cost.class),"%"+id+"%");
        return costs;
    }

    //按订单号查询
    @Override
    public Cost findFamilyByorderid(int orderid) {
        String sql = "select * from cost where orderid = ?";
        Cost cost = jdbcTemplate.queryForObject(sql, Cost.class,orderid);
        return cost;
    }


}
