package com.example.demodoerweb.service;

import model.Doer;
import model.DoerAndQuote;
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
        return "demo";
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
    public String quotesDoer(@RequestParam(name = "id") Integer id, Model model) {
        doerToModelById(id, model);
        return "quotesDoer";
    }


    private void doerToModelById(Integer id, Model model) {
        Doer doer = doerService.showDoerById(id);
        if (doer == null) {
            return;
        }
        List<DoerAndQuote> doerAndQuotes = doerService.showQuotesByDoerId(id);
        if (doerAndQuotes.get(0).getLikes() == null) {
            doerAndQuotes.get(0).setLikes(0);
        }
        String name = doer.getName();
        String surName = doer.getSurName();
        model.addAttribute("name", name + ' ' + surName);
        if (doerAndQuotes.get(0).getLikes() != null) {
            model.addAttribute("resultByShow", doerAndQuotes);
        } else {
            model.addAttribute("resultByShow", "error");
        }
    }

    @RequestMapping(value = "/likesQuote", method = RequestMethod.GET)
    public String likesQuote(@RequestParam(name = "idQuote") Integer id,
                             @RequestParam(name = "idDoer") Integer idDoer,
                             Model model) {
        if (id != 0) {
            doerService.incrementLikes(id);
            doerToModelById(idDoer, model);

        } else {
            System.out.println("0 id");
            System.out.println("idQuote" + id + "idDoer" + idDoer);
        }
        return "quotesDoer";
    }


    @GetMapping("/insertDoer")
    public String InsertDoer() {
        return "insertDoer";
    }

    @GetMapping("/mainPage")
    public String MainPage() {
        return "demo";
    }


    @GetMapping("/findQuote")
    public String FindQuotes() {
        return "FindQuote";
    }


    @RequestMapping(value = "/addDoerAndQuote", method = RequestMethod.POST)
    public String addDoerAndQuote(@RequestParam(name = "chars") String doer,
                                  @RequestParam(name = "doerSurname") String surname,
                                  @RequestParam(name = "charsQuote") String quote,
                                  Model model) {

        List<Doer> doers = doerService.findDoer(doer, surname);

        if (!doers.isEmpty()) {
            Long nextVal = doerService.nextVal();
            doerService.insertDoer(nextVal, doer, surname);
            doerService.insertQuote(quote, nextVal);
            model.addAttribute("doerName", quote + " добавлена");

            //  doerService.insertQuote(quote, doers.get(0).getId());

        } else if (doers.isEmpty()) {
            Long nextVal = doerService.nextVal();
            doerService.insertDoer(nextVal, doer, surname);
            doerService.insertQuote(quote, nextVal);
            model.addAttribute("doerName", doer + " " + surname + " " + "добавлен");

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

    @ResponseBody
    @RequestMapping(value = "/dataSearchQuote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DoerAndQuote> SearchQuoteData(@RequestParam(name = "chars") String chars) {
        return doSearchDoersAndQuote(chars);
    }


    @ResponseBody
    @RequestMapping(value = "/dataSearchQuoteByInsert", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DoerAndQuote> searchQuoteBySurname(@RequestParam(name = "charsQuote") String charsQuote,
                                                   @RequestParam(name = "chars") String charsSurName) {
        return SearchQuoteBySurname(charsQuote, charsSurName);
    }

    private List<DoerAndQuote> SearchQuoteBySurname(String charsQuote, String charsSurName) {
        if (StringUtils.isEmpty(charsQuote)) {
            return Collections.EMPTY_LIST;
        }
        List<DoerAndQuote> Quote = doerService.findQuoteBySurname(charsQuote, charsSurName);
        return Quote;
    }


    @ResponseBody
    @RequestMapping(value = "/dataSearchByInsertDoer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Doer> doerInsertSearch(@RequestParam(name = "chars") String chars) {
        return doSearchInsertDoers(chars);

    }

    private List<Doer> doSearchInsertDoers(String chars) {
        if (StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<Doer> doers = doerService.searchDoer(chars);
        return doers;
    }

    private List<Doer> doSearchDoers(String chars) {
        if (StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<Doer> doers = doerService.searchDoer(chars);
        return doers;
    }

    private List<DoerAndQuote> doSearchDoersAndQuote(String chars) {
        if (StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<DoerAndQuote> doersAndQuote = doerService.searchQuote(chars);
        return doersAndQuote;
    }

    @RequestMapping(value = "/formSearchQuote", method = RequestMethod.POST)
    public String findQuote(@RequestParam(name = "chars") String chars, Model model) {
        List<DoerAndQuote> doersAndQuote = doSearchDoersAndQuote(chars);
        model.addAttribute("doerAndQuoteResult", doersAndQuote);
        return "FindQuote";
    }

    @RequestMapping(value = "/formSearchDoerByName", method = RequestMethod.POST)
    public String findDoerSql(@RequestParam(name = "chars") String chars, Model model) {
        List<Doer> doers = doSearchDoers(chars);
        model.addAttribute("doersSearchResult", doers);
        return "doerSearch";
    }


}


