package com.example.demodoerweb.service;

import model.Doer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TestDoerController {

    // test
    private static List<Doer> doerList = new ArrayList<>();

    static {
        doerList.add(createDoer(1L, "Alexander", "?"));
        doerList.add(createDoer(2L, "Ivan", "?"));
        doerList.add(createDoer(3L, "Petr", "?"));
        doerList.add(createDoer(4L, "Slava", "?"));
        doerList.add(createDoer(5L, "Sergei", "?"));
        doerList.add(createDoer(6L, "Grigory", "?"));
        doerList.add(createDoer(7L, "Evgeni", "?"));
    }

    // test
    private static Doer createDoer(Long id, String name, String surName) {
        Doer doer = new Doer();
        doer.setId(id);
        doer.setName(name);
        doer.setSurName(surName);
        return doer;
    }


    // test
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "jsTest";
    }

    //test
    @ResponseBody
    @RequestMapping(value = "/testSearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Doer> testSearch(@RequestParam(name = "charsTest") String chars) {
        if (StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<Doer> doers = doerList.stream()
                .filter(doer -> doer.getName().contains(chars))
                .collect(Collectors.toList());
        return doers;
    }

    //test
    @RequestMapping(value = "/testFindDoer", method = RequestMethod.POST)
        public String testFindDoer(@RequestParam(name = "doerSearch") String doerSearch, Model model) {
            List<Doer> doers = doerList.stream()
                    .filter(doer -> doer.getName().contains((doerSearch)))
                    .collect(Collectors.toList());

        model.addAttribute("doersResult", doers);
        return "jsTest";
    }

}
