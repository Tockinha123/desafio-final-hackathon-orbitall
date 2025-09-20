package br.com.orbitall.channels.controllers;

import br.com.orbitall.channels.canonicals.TransactionRequest;
import br.com.orbitall.channels.canonicals.TransactionResponse;
import br.com.orbitall.channels.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionRequest));
    }
    
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> retrieveTransactionByCustomer(@RequestParam UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.retrieveTransactionsByCustomer(customerId));
    }
}
