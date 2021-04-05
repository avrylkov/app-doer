package com.example.demodoerweb.service;

import model.Admin;
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

    public List<Doer> findDoer(String surName, String name) {
        return jdbcTemplate.query(sqlFindDoer, new Object[]{surName.toUpperCase(), name.toUpperCase()}, BeanPropertyRowMapper.newInstance(Doer.class));

    }


    private static final String sqlInsertDoer = "insert into doer(id,name,surname)\n" +
            "values (?,?,?)";

    public void insertDoer(Long id, String name, String surName) {
        jdbcTemplate.update(sqlInsertDoer, id, name, surName);

    }


    public void insertQuote(String quote, Long idDoer) {
        jdbcTemplate.update(sqlInsertQuote, idDoer, quote);
    }

    private static final String sqlInsertQuote = "insert into adminTableQuote\n" +
            "  (id, id_doer, text)\n" +
            "values\n" +
            " ((select nextval('QUOTE_SEQ')), ?, ?)";


    private static final String sqlShowDoer = "select name,surname,id  from doer limit 10";

    public List<Doer> showDoer() {
        return jdbcTemplate.query(sqlShowDoer, BeanPropertyRowMapper.newInstance(Doer.class));
    }

    public List<QuoteDoer> showQuote() {
        return jdbcTemplate.query(sqlShowDoer, BeanPropertyRowMapper.newInstance(QuoteDoer.class));
    }

    private static final String sqlSortQuote = "select likes from quote order by likes desc";

    public List<DoerAndQuote> sortQuote(){
        return jdbcTemplate.query(sqlSortQuote, BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }


    private static final String sqlShowDoerById = "select * from doer where id  = ?";

    public Doer showDoerById(int id) {
        List<Doer> list = jdbcTemplate.query(sqlShowDoerById, new Object[]{id}, BeanPropertyRowMapper.newInstance(Doer.class));
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    private static final String SQL_SHOW_QUOTES_BY_DOER_ID = "select d.name,d.surname,d.id,q.text,q.likes,q.id as idQuote from doer d join quote q ON d.id = q.id_doer where d.id = ?  order by likes desc";

    public List<DoerAndQuote> showQuotesByDoerId(int id) {
        return jdbcTemplate.query(SQL_SHOW_QUOTES_BY_DOER_ID, new Object[]{id}, BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }


    private static final String sqlShowQuoteById = "select  * from quote where id_doer = ?";

    public List<QuoteDoer> showQuoteById(int id) {
        return jdbcTemplate.query(sqlShowQuoteById, new Object[]{id}, BeanPropertyRowMapper.newInstance(QuoteDoer.class));
    }


    public List<Doer> searchDoer(String chars) {
        return jdbcTemplate.query(sqlSearchDoer, new Object[]{"%" + chars.toUpperCase() + "%"}, BeanPropertyRowMapper.newInstance(Doer.class));
    }

    private static final String sqlSearchDoer = "select name, surName, id  from doer where upper (name) || ' ' || upper (surname) like ?";


    public static final String SQL_SEARCH_QUOTE = "select  d.name,d.surname,d.id,q.likes,q.text,q.id as idQuote from doer d join quote q ON d.id = q.id_doer where upper (q.text) like ?";

    public List<DoerAndQuote> searchQuote(String chars) {
        return jdbcTemplate.query(SQL_SEARCH_QUOTE, new Object[]{"%" + chars.toUpperCase() + "%"}, BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }

    public static final String sqlFindQuoteBySurname = "select d.name,d.surname,d.id,q.likes,q.text,q.id as idQuote from doer d join quote q ON d.id = q.id_doer where q.text like ? and d.surname = ?";

    public List<DoerAndQuote> findQuoteBySurname(String chars, String surname) {
        return jdbcTemplate.query(sqlFindQuoteBySurname, new Object[]{"%" + chars + "%", surname}, BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }

    public final static String sqlUpdateLikes = "update quote set likes  = ? where id  = ?";

    public final static String sqlSelectLikes = "select likes from quote where  id = ?";

    public Integer incrementLikes(int id) {
        Integer count = jdbcTemplate.queryForObject(sqlSelectLikes, Integer.class, id);
        count = count == null ? 1 : count + 1;
        jdbcTemplate.update(sqlUpdateLikes, count, id);
        return count;

    }


    public  static final String sqlInsertIntoAdminsTable = "insert into admins(login, password)\n" +
            "values (?,?)";

    public void insertAdminsToAdminTable(String login,String password){
        jdbcTemplate.update(sqlInsertIntoAdminsTable,login,password);
    }

    public  static  final  String sqlSelectAdminFromAdminsTable = "select * from Admins where login = ? and password =  ?";

    public List<Admin> findAdminFromAdminsTable(String login,String password){
        return  jdbcTemplate.query(sqlSelectAdminFromAdminsTable, new Object[]{login,password}, BeanPropertyRowMapper.newInstance(Admin.class));
    }

    public  static  final  String sqlSelectDoerAndQuoteFromAdminTable = "select  d.name,d.surname,d.id,q.likes,q.text,q.id as idQuote from doer d join adminTableQuote q ON d.id = q.id_doer";

    public List<DoerAndQuote> selectDoerAndQuoteByAdminTable(){
        return  jdbcTemplate.query(sqlSelectDoerAndQuoteFromAdminTable,BeanPropertyRowMapper.newInstance(DoerAndQuote.class));
    }

    public  static final String sqlInsertIntoMainTableFromAdmin = "insert into quote(id,id_doer,text)\n" +
            "values(?,?,?);";
    public void insertIntoMainTableFromAdmin(Integer idQuote, Integer idDoer, String textQuote){
         jdbcTemplate.update(sqlInsertIntoMainTableFromAdmin,idQuote,idDoer,textQuote);
    }

    public static final String sqlDeleteQuoteFromAdminTable = "delete from adminTableQuote where id_doer = ? and id = ?";

    public  void deleteQuoteFromAdminTable(Integer idDoer,Integer idQuote){
        jdbcTemplate.update(sqlDeleteQuoteFromAdminTable,idDoer,idQuote);
    }



}
