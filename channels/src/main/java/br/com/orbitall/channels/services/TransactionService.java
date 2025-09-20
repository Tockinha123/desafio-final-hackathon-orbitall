package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.TransactionRequest;
import br.com.orbitall.channels.canonicals.TransactionResponse;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.models.Transaction;
import br.com.orbitall.channels.repositories.CustomerRepository;
import br.com.orbitall.channels.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public TransactionService(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        Customer customer = customerRepository
                .findById(transactionRequest.customerId())
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));


        Transaction transaction = Transaction.builder()
                .customerId(transactionRequest.customerId())
                .amount(transactionRequest.amount())
                .cardType(transactionRequest.cardType())
                .active(true)
                .build();

        transactionRepository.save(transaction);

        return new TransactionResponse(
                transaction.getId(),
                transaction.getCustomerId(),
                transaction.getAmount(),
                transaction.getCardType(),
                transaction.getCreatedAt(),
                transaction.isActive()
        );
    }

    public List<TransactionResponse> retrieveTransactionsByCustomer(UUID customerId) {

        Customer customer = customerRepository
                .findById(customerId)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<TransactionResponse> transactions = new ArrayList<>();

        transactionRepository.findByCustomerId(customerId).forEach(
                transaction -> {

                    if(transaction.isActive()) {
                        TransactionResponse transactionResponse = new TransactionResponse(
                                transaction.getId(),
                                transaction.getCustomerId(),
                                transaction.getAmount(),
                                transaction.getCardType(),
                                transaction.getCreatedAt(),
                                transaction.isActive()
                        );

                        transactions.add(transactionResponse);
                    }
                }
        );

        return transactions;
    }
}

















































/*
*
*
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠤⠒⠒⠉⠉⠉⠛⠳⢦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⡆⠀⠀⠀⠀⢀⡴⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⡈⠙⢦⡀⠀⠀⠀⣶⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⢧⠀⠀⠀⢀⣿⠀⠀⣰⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⠀⠀⢹⠀⠀⠀⡿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⣠⠏⠸⡄⠀⣰⢺⡟⠀⠀⢿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠁⠀⢸⣆⠀⢀⡇⠘⢆⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⢀⡤⠚⠁⠀⠀⡇⠀⣳⣼⠀⠀⠀⣾⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡆⠀⠀⣿⡀⢸⡇⠀⠀⠙⠦⡀⠀⠀⠀⠀⠀
⠀⠀⠀⢠⠖⠁⠀⠀⠀⠀⢀⣷⣴⣿⠏⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⡇⠀⠀⠘⣷⣾⣀⠀⠀⠀⠀⠈⠳⡄⠀⠀⠀
⠀⠀⠀⢸⣤⣀⠀⢀⣴⡾⢏⠀⢀⠏⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠹⡄⠈⣿⣦⡀⠀⠀⣀⡇⠀⠀⠀
⠀⠀⠀⠈⡇⣀⡉⡛⠇⠀⠈⣧⡎⢀⣀⣀⣀⣀⣿⠐⢦⡀⠀⠀⠀⢀⡠⠔⢀⣧⣀⣀⣀⣀⠹⣤⠁⡇⢹⠊⠁⢀⡇⠀⠀⠀
⠀⠀⠀⠀⢹⠀⣀⣡⢸⠀⠀⢻⠀⡏⢫⡹⡿⡙⠻⡝⢲⡽⠂⠀⠀⠁⠰⣯⣿⡟⠉⢉⡽⠉⣧⡿⠀⢰⢸⠋⠉⢹⠁⠀⠀⠀
⠀⠀⠀⠀⠘⡍⠁⠈⢾⣇⠀⢸⡇⢇⠀⠙⠓⢒⣫⡷⠋⠀⠀⠀⠀⠀⠀⠈⢫⣏⠛⠉⠀⢠⢿⡇⠀⣮⡎⠉⠉⣹⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠻⡌⠁⠀⠙⢆⣸⣷⠈⠓⣒⡿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠛⠶⣖⡉⣸⣃⡼⠟⠉⠉⡩⠋⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠈⢦⠀⠀⠀⠉⠻⢷⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡼⠛⠁⠀⠀⢀⠞⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠑⣄⠀⠀⠀⠸⡄⠈⠓⢆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠖⠉⢸⠁⠀⠀⠀⣠⠋⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢣⡀⠀⠀⢷⣤⠤⢼⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠢⣤⣌⠀⠀⢀⡼⠁⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣷⡀⠀⠸⣟⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⡏⠀⢀⣞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⣠⢴⣵⠏⠀⣻⣆⠀⣿⣿⠉⡇⠀⢰⣦⢠⡆⢴⡀⢶⠀⢠⣿⣿⣿⠃⢠⣏⠈⠳⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣠⣾⡿⢋⡏⠀⣰⠃⠻⣄⢹⡇⠀⢃⠀⣾⢿⡏⣥⣦⣧⠀⣇⢸⠁⠉⡿⢀⡇⠈⢣⠀⢹⣶⣄⠀⠀⠀⠀⠀⠀
⠀⠀⠀⢀⡴⣿⠏⠀⢸⡇⠀⣯⣠⣾⠿⣆⣇⢠⣧⣴⣿⣼⡇⣿⣿⣿⠀⣿⣬⣧⠀⣇⡼⢷⣄⠈⡇⠀⡿⢍⢷⣄⠀⠀⠀⠀
⠀⠀⢀⡞⡼⠁⠀⠀⠸⡇⠀⠸⡟⠁⠀⢹⡼⠘⠁⢸⠸⡏⢹⡿⢿⣿⣾⣿⠀⡇⠀⡿⠃⠀⠻⣿⠃⢠⠇⠀⠳⡙⢦⡀⠀⠀
⠀⠀⡞⢠⠁⠀⠀⠀⠀⢻⡄⠀⠱⣄⡤⠤⢧⠀⠀⢸⢀⣡⣾⣷⢦⣄⡉⣿⠀⡇⣸⠉⠑⠲⣴⠃⢠⡎⠀⠀⠀⠹⡄⢳⡀⠀
⠀⢰⠃⢸⠀⠀⠀⢀⣠⣼⠿⣄⠀⠱⡄⠀⠸⡄⡄⢸⠿⡟⠛⠉⠉⠛⡿⡟⠀⣇⠇⠀⢠⠞⠁⢠⢿⣷⣤⡀⠀⠀⢻⠘⣇⠀
⢀⡇⠀⢸⠀⢀⠖⠋⣿⡏⠀⠙⣦⠀⠙⣦⡀⣇⠸⡶⠀⢿⠀⠀⠀⢰⠃⢷⠎⡟⠀⡰⠋⠀⡰⠋⠀⣿⡌⠻⡆⠀⢸⠀⢻⡀
⢸⠀⠀⠈⢧⠎⠀⠘⣟⢧⣀⠀⠈⠳⣄⠈⠻⣾⡀⢱⠀⠈⡇⣀⣠⡏⢀⠏⣀⡷⠚⢀⡴⠏⠀⢀⣠⢿⠇⠀⢳⣠⠃⠀⠘⡇
⠀⠀⠀⠀⠈⠳⡀⠀⠈⠙⠒⠭⠭⢷⣚⣳⣄⠈⣹⣾⠓⢻⡉⠀⠀⢹⠛⢿⣅⢀⣴⢿⣵⣒⣯⠥⠚⠉⠀⣠⡿⠁⠀⠀⠀⠁
⠀⠀⠀⠀⠀⠀⠈⠒⢤⣀⡀⠀⠀⠀⠀⠈⣹⠟⠉⢹⠀⠈⠳⣄⣠⠞⠀⢸⠏⠻⡉⠉⠁⠀⠀⠀⢀⣤⠞⠋⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⢀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀
*
* GENERAL GRIEVOUS O MELHOR PERSONAGEM DE STAR WARS :D
* */