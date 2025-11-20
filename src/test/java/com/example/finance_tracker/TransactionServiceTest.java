package com.example.finance_tracker;

import com.example.finance_tracker.dto.TransactionRequest;
import com.example.finance_tracker.models.Account;
import com.example.finance_tracker.repositories.AccountRepository;
import com.example.finance_tracker.repositories.TransactionRepository;
import com.example.finance_tracker.servicies.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

class TransactionServiceTest {

    TransactionRepository txRepo = mock(TransactionRepository.class);
    AccountRepository accountRepo = mock(AccountRepository.class);

    TransactionService service = new TransactionService(txRepo, accountRepo);

    @Test
    void createTransaction_Saves() {

        Account acc = new Account();
        acc.setId(1L);
        acc.setBalance(BigDecimal.ZERO);

        when(accountRepo.findById(1L)).thenReturn(Optional.of(acc));

        TransactionRequest req = new TransactionRequest(
                "Shop",
                BigDecimal.valueOf(100),
                "FOOD",
                OffsetDateTime.now(),
                "notes",
                Set.of("tag1")
        );

        service.create(1L, req);

        verify(txRepo, times(1)).save(any());
        verify(accountRepo, times(1)).save(acc);
    }
}
