package com.example.finance_tracker.controllers;

import com.example.finance_tracker.config.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
            // not authenticated -> login
            return "redirect:/login";
        }
        UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
        Long userId = ud.getUserAccount().getProfile().getId();
        // redirect to user's dashboard
        return "redirect:/" + userId + "/dashboard";
    }
}
