//package ru.otus.controllers;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

//работает  - но не понятно как получить параметры ошибки
//@Controller
//public class CustomErrorController implements ErrorController {
//    @RequestMapping("/error")
//    @ResponseBody
//    String error(Model model, HttpServletRequest request) {
//        return "<h1>Error occurred</h1>";
//    }
//}