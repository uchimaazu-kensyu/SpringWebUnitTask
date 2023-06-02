package com.example.springWebTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

import java.util.List;


@SpringBootApplication
public class SpringWebTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebTaskApplication.class, args);


	}

}
