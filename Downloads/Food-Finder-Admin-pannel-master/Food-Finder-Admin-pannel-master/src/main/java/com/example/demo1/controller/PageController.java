//package com.example.demo1.controller;
//
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class PageController {
//
//    @GetMapping("/index.html")
//    public String protectIndex(HttpSession session) {
//        Object user = session.getAttribute("user");
//        if (user == null) {
//            // ❌ Not logged in → redirect to login
//            return "redirect:/Auth.html";
//        } else {
//            // ✅ Logged in → allow static file
//            return "index.html";
//        }
//    }
//}
