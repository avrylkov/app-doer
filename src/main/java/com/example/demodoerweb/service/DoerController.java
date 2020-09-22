package com.example.demodoerweb.service;

import model.Doer;
import model.QuoteDoer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class DoerController {

    @Autowired
    private DoerService doerService;

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/findDoerPage")
    public String mainShowDoer(Model model) {
        List<Doer> doers = doerService.showDoer();
        model.addAttribute("doers", doers);
        return "QuotesDoer";
    }

    @RequestMapping(value = "quote", method = RequestMethod.POST)
    public String getQuote(@RequestParam(name = "doer") String doerName, Model model) {
        List<QuoteDoer> quoteDoers = doerService.getQuotes(doerName);

        String surname = "";
        String sname;
        if (!quoteDoers.isEmpty()) {//если лист не пустой
            sname = quoteDoers.get(0).getName();// из листа классов qouteDoer у класса получам имя и фамили.
            surname = quoteDoers.get(0).getSurName();
        } else {
            sname = "Не найден";//если такого нет то
        }
        model.addAttribute("name", sname + " " + surname);//замена параметра name html
        model.addAttribute("quotes", quoteDoers);
        return "quotePage";
    }

    @RequestMapping(value = "/QuotesDoer", method = RequestMethod.GET)
    public String QuotesDoer(@RequestParam(name = "id") Integer id, Model model) {
        List<Doer> doers  = doerService.showDoerById(id);
        List<QuoteDoer> quotes  = doerService.showQuoteById(id);
        String name  = doers.get(0).getName();
        String surName  = doers.get(0).getSurName();
        model.addAttribute("name",name + ' ' + surName);
        model.addAttribute("quotes",quotes);
        System.out.println(id);
        return "QuotesDoer";
    }

    @GetMapping("/mainPage")
    public  String mainPageShow(){
        return "mainPage";
    }

    @GetMapping("/insertDoer")
    public String InsertDoer() {
        return "insertDoer";
    }

    @GetMapping("/quotePage")
    public String getFindQuotes() {
        return "quotePage";
    }

    @RequestMapping(value = "/addQuote", method = RequestMethod.POST)
    public String addQuote(@RequestParam(name = "doerName") String doer,
                           @RequestParam(name = "doerSurname") String surname,
                           @RequestParam(name = "quote") String quote,
                           Model model) {

        List<Doer> doers = doerService.getDoer(doer, surname);

        if (!doers.isEmpty()) {
            model.addAttribute("doerName",doer + " " +surname + " " + "добавлен");
            doerService.insertQuote(quote,doers.get(0).getId());
        } else if(doers.isEmpty()){
            Long nextVal = doerService.nextVal();
            doerService.insertDoer(nextVal,doer,surname);
            doerService.insertQuote(quote,nextVal);

            model.addAttribute("doerName", "Добавлен - " + doer);
        }

        return "insertDoer";
    }

    @RequestMapping(value = "/doerSearchPage", method = RequestMethod.GET)
    public String doerSearchPage(Model model) {
        List<Doer> doers = doerService.showDoer();
        model.addAttribute("doers", doers);
        return "doerSearch";
    }

    @ResponseBody
    @RequestMapping(value = "/dataSearchDoerByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Doer> SearchSql(@RequestParam(name = "chars") String chars) {
        return doSearchDoers(chars);
    }

    //test
    @RequestMapping(value = "/formSearchDoerByName", method = RequestMethod.POST)
    public String findDoerSql(@RequestParam(name = "chars") String chars, Model model) {
        List<Doer> doers = doSearchDoers(chars);
        model.addAttribute("doersSearchResult", doers);
        return "doerSearch";
    }

    private List<Doer> doSearchDoers(String chars) {
        if (StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<Doer> doers = doerService.searchDoer(chars);
        return doers;
    }



}


