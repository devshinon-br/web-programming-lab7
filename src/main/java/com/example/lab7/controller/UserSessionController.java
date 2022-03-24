package com.example.lab7.controller;

import com.example.lab7.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserSessionController {
    @GetMapping("/")
    public String process(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        model.addAttribute("sessionId", session.getId());
        User storedUser = (User) session.getAttribute("SESSION_USER_DATA");
        if (storedUser != null) {
            model.addAttribute("userData", storedUser);
        }
        return "index";
    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (messages == null) {
            messages = new ArrayList<>();
            session.setAttribute("MY_SESSION_MESSAGES", messages);
        }
        messages.add(msg);
        session.setAttribute("MY_SESSION_MESSAGES", messages);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String saveUserData(User user, Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        User storedUser = (User) session.getAttribute("SESSION_USER_DATA");
        if (storedUser == null) {
            session.setAttribute("SESSION_USER_DATA", user);
            model.addAttribute("userData", user);
        }
        else {
            model.addAttribute("userData", storedUser);
        }
        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}