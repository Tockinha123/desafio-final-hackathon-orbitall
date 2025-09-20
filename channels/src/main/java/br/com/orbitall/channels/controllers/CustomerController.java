package br.com.orbitall.channels.controllers;

import br.com.orbitall.channels.canonicals.CustomerRequest;
import br.com.orbitall.channels.canonicals.CustomerResponse;
import br.com.orbitall.channels.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> retrieve(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.retrieve(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> retrieveAll() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.retrieveAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id, @RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(id, customerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.delete(id));
    }
}
