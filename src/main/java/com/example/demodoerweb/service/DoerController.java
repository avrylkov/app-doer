package com.example.demodoerweb.service;

import model.Doer;
import model.QuoteDoer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class DoerController {

    @Autowired
    private DoerService doerService;

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/findDoer")
    public String mainShowDoer(Model model) {
        List<Doer> doers = doerService.showDoer();
        model.addAttribute("doers", doers);
        return "findDoer";
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


    //@GetMapping("/findDoer")
   // public String getFindDoer() {
      //  return "insertDoer";
   // }
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

    @RequestMapping(value = "/toDoer", method = RequestMethod.GET)
    public String toDoer(@RequestParam(name = "id") String id) {
        System.out.println(id);
        return "jsTest";
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
      return "jsTest";
    }

    @ResponseBody
    @RequestMapping(value = "/testSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Doer> testSearch(@RequestParam(name = "chars") String chars) {
        Doer doer = new Doer();
        doer.setId(1L);
        doer.setName("Alex");
        doer.setSurName(chars);
        return Arrays.asList(doer);
    }

}


