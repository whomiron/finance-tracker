package com.example.finance_tracker.controllers;

import com.example.finance_tracker.forms.RegistrationForm;
import com.example.finance_tracker.models.Account;
import com.example.finance_tracker.models.UserAccount;
import com.example.finance_tracker.models.UserProfile;
import com.example.finance_tracker.repositories.AccountRepository;
import com.example.finance_tracker.repositories.UserAccountRepository;
import com.example.finance_tracker.repositories.UserProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;

@Controller
public class RegistrationController {

    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public RegistrationController(UserAccountRepository userAccountRepository,
                                  UserProfileRepository userProfileRepository,
                                  PasswordEncoder passwordEncoder,
                                  AccountRepository accountRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("form") RegistrationForm form, Model model) {
        if (form.getUsername() == null || form.getPassword() == null ||
                form.getUsername().isBlank() || form.getPassword().isBlank()) {
            model.addAttribute("error", "Введите имя пользователя и пароль");
            return "register";
        }

        if (userAccountRepository.findByUsername(form.getUsername()).isPresent()) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "register";
        }

        UserProfile profile = new UserProfile();
        profile.setDisplayName(form.getDisplayName() != null && !form.getDisplayName().isBlank()
                ? form.getDisplayName() : form.getUsername());
        userProfileRepository.save(profile);

        UserAccount user = new UserAccount();
        user.setUsername(form.getUsername());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setProfile(profile);
        if (user.getRoles() == null) user.setRoles(new HashSet<>());
        user.getRoles().add("ROLE_USER");

        userAccountRepository.save(user);

        Account account = new Account();
        account.setName("Основной счёт");
        account.setBalance(BigDecimal.ZERO);
        account.setOwner(profile);
        accountRepository.save(account);

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLogin() { return "login"; }
}
