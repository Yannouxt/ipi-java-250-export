package com.example.demo.controller;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
import com.example.demo.service.InitData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FactureService factureService;

    @Autowired
    private InitData initData;

    @PostConstruct
    public void initTestData() {
        initData.insertTestData();
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }
    
    @GetMapping("/clients")
    public ModelAndView clients() {
        ModelAndView modelAndView = new ModelAndView("clients");

        List<ClientDTO> clients = clientService.findAllClients();
        modelAndView.addObject("clients", clients);

        return modelAndView;
    }
    
    @GetMapping("/factures")
    public ModelAndView factures() {
        ModelAndView modelAndView = new ModelAndView("factures");

        List<FactureDTO> factures = factureService.findAllFactures();
        modelAndView.addObject("factures", factures);

        return modelAndView;
    }
}
