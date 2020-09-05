package com.example.demodoerweb.service;

import model.QuoteDoer;
import model.Doer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoerService {

    private static final String sqlFindQuote = "select d.surname, d.name, q.text\n" +
            "  from doer d\n" +
            "  join quote q\n" +
            "    on d.id = q.id_doer\n" +
            " where upper(d.surname) = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String sqlSelectQuote = "insert into quote\n" +
            "  (id, id_doer, text)\n" +
            "values\n" +
            " (quote_seq.nextval, ?, ?)";

    private static final String sqlInsertDoer = "insert into doer(id,name,surname)\n" +
            "values (?,?,?)";


    public List<QuoteDoer> getQuotes(String doerName) {
        return jdbcTemplate.query(sqlFindQuote, new Object[]{doerName.toUpperCase()}, BeanPropertyRowMapper.newInstance(QuoteDoer.class));
    }

    private static final String sqlFindDoer = " select surName,name,id from doer" +
            " where upper (surName)  = ? AND upper (name)= ?";

    public List<Doer> getDoer(String surName, String name) {
        return jdbcTemplate.query(sqlFindDoer, new Object[]{surName.toUpperCase(), name.toUpperCase()}, BeanPropertyRowMapper.newInstance(Doer.class));

    }

    private static final String sqlNextval = "SELECT doer_seq.nextval FROM DUAL";

    public void insertDoer(Long id, String name, String surName) {
        jdbcTemplate.update(sqlInsertDoer, id, name, surName);

    }

    public Long nextVal() {
        return jdbcTemplate.queryForObject(sqlNextval, Long.class);
    }

    public void insertQuote(String quote, Long idDoer) {
        jdbcTemplate.update(sqlSelectQuote, idDoer, quote);
    }

    public void testConnection() {
        jdbcTemplate.queryForList("select * from test");
    }
}
