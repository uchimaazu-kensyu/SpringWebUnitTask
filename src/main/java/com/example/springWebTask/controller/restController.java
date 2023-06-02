package com.example.springWebTask.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//TestControllerからコピー

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.example.springWebTask.controller.*;
import com.example.springWebTask.service.*;
import com.example.springWebTask.entity.*;
import com.example.springWebTask.dao.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class restController {
    @Autowired
    private ProductService productService;





    @GetMapping("/products")
    public List<DisplayProduct> findAll2(){
        try{
            List<DisplayProduct> productList =productService.findAll();
            return productList ;
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/products/{searchInput}")
    public List<DisplayProduct> search(@PathVariable("searchInput")String searchTarget){

        try{
            List<DisplayProduct> list =productService.findByName(searchTarget);
            System.out.println(list);
            return list;
        }catch (RuntimeException e){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }





}
