package com.example.finance_tracker.controllers;

import com.example.finance_tracker.config.UserDetailsImpl;
import com.example.finance_tracker.dto.TransactionRequest;
import com.example.finance_tracker.forms.TransactionForm;
import com.example.finance_tracker.servicies.TransactionService;
import com.example.finance_tracker.util.TransactionCategory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/{userId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private Long currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
            throw new IllegalStateException("User not authenticated");
        }
        return ((UserDetailsImpl) auth.getPrincipal()).getUserAccount().getProfile().getId();
    }

    @GetMapping
    public String list(@PathVariable Long userId, Model model) {
        if (!userId.equals(currentUserId())) {
            return "redirect:/" + currentUserId() + "/transactions";
        }

        var transactions = transactionService.listByUser(userId);

        TransactionForm form = new TransactionForm();
        form.setUserId(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionForm", form);
        model.addAttribute("categories", TransactionCategory.getAll());
        return "transactions";
    }

    @PostMapping
    public String create(@PathVariable Long userId,
                         @ModelAttribute("transactionForm") TransactionForm form) {
        if (!userId.equals(currentUserId())) {
            return "redirect:/" + currentUserId() + "/transactions";
        }

        OffsetDateTime occurredAt = form.getOccurredAt() != null
                ? form.getOccurredAt().atZone(ZoneId.systemDefault()).toOffsetDateTime()
                : null;

        TransactionRequest request = new TransactionRequest(
                form.getMerchant(),
                form.getAmount(),
                form.getCategory(),
                occurredAt,
                form.getNotes(),
                extractTags(form.getTagsInput())
        );

        transactionService.create(userId, request);
        return "redirect:/" + userId + "/transactions";
    }

    private Set<String> extractTags(String tagsInput) {
        if (tagsInput == null || tagsInput.isBlank()) return null;
        return new HashSet<>(Arrays.stream(tagsInput.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList());
    }
}
