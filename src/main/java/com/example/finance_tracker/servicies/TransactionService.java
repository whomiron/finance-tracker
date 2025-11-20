package com.example.finance_tracker.servicies;

import com.example.finance_tracker.models.Account;
import com.example.finance_tracker.models.Transaction;
import com.example.finance_tracker.dto.TransactionRequest;
import com.example.finance_tracker.dto.TransactionResponse;
import com.example.finance_tracker.repositories.AccountRepository;
import com.example.finance_tracker.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponse create(Long userId, TransactionRequest request) {

        Account account = accountRepository.findByOwnerId(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        Transaction tx = new Transaction();
        tx.setMerchant(request.getMerchant());
        tx.setAmount(request.getAmount());
        tx.setAccount(account);
        tx.setCategory(request.getCategory());
        tx.setOccurredAt(request.getOccurredAt());
        tx.setNotes(request.getNotes());
        tx.setTags(normalizeTags(request.getTags()));

        Transaction saved = transactionRepository.save(tx);

        return mapToResponse(saved);
    }

    public List<TransactionResponse> listByUser(Long userId) {

        List<Account> accounts = accountRepository.findByOwnerId(userId);

        if (accounts.isEmpty()) {
            return List.of();
        }

        Long accountId = accounts.get(0).getId();

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private Set<String> normalizeTags(Set<String> tags) {
        if (tags == null) return null;
        return tags.stream()
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(HashSet::new));
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getMerchant(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getOccurredAt(),
                transaction.getNotes(),
                transaction.getTags()
        );
    }
}
