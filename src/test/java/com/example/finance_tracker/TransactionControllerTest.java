package com.example.finance_tracker;

import com.example.finance_tracker.controllers.TransactionController;
import com.example.finance_tracker.servicies.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(TransactionControllerTest.MockConfig.class)
class TransactionControllerTest {

    @Configuration
    static class MockConfig {
        @Bean
        TransactionService transactionService() {
            return Mockito.mock(TransactionService.class);
        }
    }

    @Autowired
    MockMvc mvc;

    @Test
    void testTransactionsPage() throws Exception {
        mvc.perform(get("/1/transactions"))
                .andExpect(status().is3xxRedirection());
    }
}
