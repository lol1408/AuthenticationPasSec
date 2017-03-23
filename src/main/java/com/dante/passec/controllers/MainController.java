package com.dante.passec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class MainController {

    @RequestMapping(value = {"/", "/home"}, method = GET)
    public String homePage(){
        return "index";
    }
    @RequestMapping(value = {"/registration"}, method = GET)
    public String regPage(){
        return "registration";
    }
    @RequestMapping(value = {"res"}, method = GET)
    public String resPage(){return "resources";}
}
