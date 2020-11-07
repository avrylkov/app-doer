package com.example.demodoerweb;

import com.example.demodoerweb.service.DemoDoerWebApplication;
import model.Doer;
import model.DoerAndQuote;
import model.QuoteDoer;
import com.example.demodoerweb.service.DoerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoDoerWebApplication.class)
class DemoDoerWebApplicationTests {

    @Autowired
    public DoerService doerService;

    @Test
    public void testQuotes() {
        List<QuoteDoer> quotes = doerService.getQuotes("ЭЙНШТЕЙН");
        Assert.assertTrue(!quotes.isEmpty());
    }

    @Test
    public Long testSearchDoer() {
        List<Doer> doers = doerService.getDoer("Эйнштейн", "АЛЬБЕРТ");
        Assert.assertTrue(!doers.isEmpty());
        return doers.get(0).getId();
    }

    @Test
    public void testInsert() {
        doerService.insertQuote("fff", 2L);
    }

    @Test
    public void testNextval() {
        Long nextVal = doerService.nextVal();
        Assert.assertTrue(nextVal > 0);

    }

    @Test
    public void insertDoer() {
        doerService.insertDoer(doerService.nextVal(), "макараси", "эйнштейн");
    }

    @Test
    public void testConnection() {
        doerService.testConnection();
    }
    @Test
    public  void testDoerSearch(){
        List<Doer> doers = doerService.searchDoer("Э");
        Assert.assertTrue(!doers.isEmpty());
    }

    @Test
    public  void testDoerAndQuote(){
       List<DoerAndQuote> doerAndQuotes  = doerService.showQuotesByDoerId(2);
        Assert.assertTrue(!doerAndQuotes.isEmpty());
    }

    @Test
    public  void testFindQuoteBySurname(){
        List<DoerAndQuote> quotesBySurname  = doerService.findQuoteBySurname("а","Медведев");
        Assert.assertTrue(!quotesBySurname.isEmpty());
    }

    @Test
    public void updateLikess(){

        Integer likes1 = doerService.incrementLikes(100);
        Integer likes2 = doerService.incrementLikes(100);
        Assert.assertEquals(likes2 - likes1, 1);
    }





}
