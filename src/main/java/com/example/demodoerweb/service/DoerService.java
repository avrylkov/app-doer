package com.example.demodoerweb.service;

import model.DoerAndQuote;
import model.QuoteDoer;
import model.Doer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoerService {

    public void testConnection() {
        jdbcTemplate.queryForList("select * from doer");

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;




    private static final String sqlNextval = "select nextval('DOER_SEQ')";

    public Long nextVal() {
        return jdbcTemplate.queryForObject(sqlNextval, Long.class);
    }



    private static final String sqlFindQuote = "select d.surname, d.name, q.text\n" +
            "  from doer d\n" +
            "  join quote q\n" +
            "    on d.id = q.id_doer\n" +
            " where upper(d.surname) = ?";

    public List<QuoteDoer> getQuotes(String doerName) {
        return jdbcTemplate.query(sqlFindQuote, new Object[]{doerName.toUpperCase()}, BeanPropertyRowMapper.newInstance(QuoteDoer.class));
    }



    private static final String sqlFindDoer = " select surName,name,id from doer" +
            " where upper (surName)  = ? AND upper (name)= ?";

    public List<Doer> getDoer(String surName, String name) {
        return jdbcTemplate.query(sqlFindDoer, new Object[] {surName.toUpperCase(), name.toUpperCase()}, BeanPropertyRowMapper.newInstance(Doer.class));

    }






    private static final String sqlInsertDoer = "insert into doer(id,name,surname)\n" +
            "values (?,?,?)";

    public void insertDoer(Long id, String name, String surName) {
        jdbcTemplate.update(sqlInsertDoer, id, name, surName);

    }



    public void insertQuote(String quote, Long idDoer) {
        jdbcTemplate.update(sqlSelectQuote, idDoer, quote);
    }

    private static final String sqlSelectQuote = "insert into quote\n" +
            "  (id, id_doer, text)\n" +
            "values\n" +
            " ((select nextval('QUOTE_SEQ')), ?, ?)";




    private static final String sqlShowDoer = "select name,surname,id  from doer limit 10";

    public List<Doer> showDoer() {
        return jdbcTemplate.query(sqlShowDoer, BeanPropertyRowMapper.newInstance(Doer.class));
    }




    private  static  final String sqlShowDoerById  = "select * from doer where id  = ?";

    public  List<Doer> showDoerById(int id){
        return jdbcTemplate.query(sqlShowDoerById, new Object[] {id}, BeanPropertyRowMapper.newInstance(Doer.class));
    }




    private  static  final String SQL_SHOW_QUOTES_BY_DOER_ID = "select d.name,d.surname,d.id,q.text from doer d join quote q ON d.id = q.id_doer where d.id = ?";

    public  List<DoerAndQuote> showQuotesByDoerId(int id){
        return jdbcTemplate.query(SQL_SHOW_QUOTES_BY_DOER_ID, new Object[] {id}, BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }



    private  static  final String sqlShowQuoteById = "select  * from quote where id_doer = ?";

    public  List<QuoteDoer> showQuoteById(int id){
        return jdbcTemplate.query(sqlShowQuoteById, new Object[] {id}, BeanPropertyRowMapper.newInstance(QuoteDoer.class));
    }




    public  List<Doer> searchDoer(String chars) {
        return  jdbcTemplate.query(sqlSearchDoer, new Object[] {"%" + chars.toUpperCase() + "%"},  BeanPropertyRowMapper.newInstance(Doer.class));
    }
    private  static  final String sqlSearchDoer = "select name, surName, id  from doer where upper (name) || ' ' || upper (surname) like ?";




    public  static  final String SQL_SEARCH_QUOTE = "select  d.name,d.surname,d.id,q.text from doer d join quote q ON d.id = q.id_doer where upper (q.text) like ?";

    public  List<DoerAndQuote> searchQuote(String chars) {
        return jdbcTemplate.query(SQL_SEARCH_QUOTE, new Object[]{"%" + chars.toUpperCase() + "%"}, BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }
}
