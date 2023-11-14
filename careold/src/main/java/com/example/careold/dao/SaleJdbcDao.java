package com.example.careold.dao;
import com.example.careold.domain.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SaleJdbcDao implements SaleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int save(Sale sale) {
        String sql="insert into sale(saccount,spassword,sname,semail,stime) VALUES(?,?,?,?,?)";
        int row= jdbcTemplate.update(sql,sale.getSaccount(),"123456",sale.getSname(),sale.getSemail(),sale.getStime());
        return row;
    }

    @Override
    public int update(Sale sale) {
        String sql="UPDATE sale SET sname=?,semail=?,stime=? WHERE saccount=?";
        int rows=jdbcTemplate.update(sql,sale.getSname(),sale.getSemail(),sale.getStime(),sale.getSaccount());
        return rows;
    }

    @Override
    public int deleteByAccount(String account) {
        String sql="DELETE FROM sale WHERE saccount=?";
        int row =jdbcTemplate.update(sql,account);
        return row;
    }


    @Override
    public Sale findByAccount(String account) {
        String sql="SELECT * FROM sale WHERE saccount=?";
        Sale sale = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Sale.class), account);
        return sale;
    }

    @Override
    public List<Sale> findAll() {
        String sql="SELECT * FROM sale ";
        List<Sale> sales = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Sale.class));
        return sales;
    }

    @Override
    public int updatePassword(Sale sale) {
        String sql="UPDATE sale SET spassword=? WHERE saccount=?";
        int rows=jdbcTemplate.update(sql,sale.getSpassword(),sale.getSaccount());
        return rows;
    }
}
