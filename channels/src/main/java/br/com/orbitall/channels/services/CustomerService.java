package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.CustomerRequest;
import br.com.orbitall.channels.canonicals.CustomerResponse;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse create (CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .fullName(customerRequest.fullName())
                .email(customerRequest.email())
                .phone(customerRequest.phone())
                .active(true)
                .build();

        customerRepository.save(customer);

        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isActive()
        );
    }

    public CustomerResponse retrieve (UUID customerId){
        Customer fetched = customerRepository
                .findById(customerId)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("No Customer found with id " + customerId));

        return new CustomerResponse(
                fetched.getId(),
                fetched.getFullName(),
                fetched.getEmail(),
                fetched.getPhone(),
                fetched.getCreatedAt(),
                fetched.getUpdatedAt(),
                fetched.isActive()
        );
    }

    public List<CustomerResponse> retrieveAll (){
        List<CustomerResponse> customerResponses = new ArrayList<>();

        customerRepository.findAll().forEach(

                customer -> {

                    if(customer.isActive()){

                        CustomerResponse customerResponse = new CustomerResponse(
                                customer.getId(),
                                customer.getFullName(),
                                customer.getEmail(),
                                customer.getPhone(),
                                customer.getCreatedAt(),
                                customer.getUpdatedAt(),
                                customer.isActive()
                        );

                        customerResponses.add(customerResponse);
                    }
                }
        );

        return customerResponses;
    }

    public CustomerResponse update (UUID customerId, CustomerRequest customerRequest){
        Customer fetched = customerRepository
                .findById(customerId)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("No Customer found with id " + customerId));

        fetched.setFullName(customerRequest.fullName());
        fetched.setEmail(customerRequest.email());
        fetched.setPhone(customerRequest.phone());
        fetched.setUpdatedAt(LocalDateTime.now());

        customerRepository.save(fetched);

        return new CustomerResponse(
                fetched.getId(),
                fetched.getFullName(),
                fetched.getEmail(),
                fetched.getPhone(),
                fetched.getCreatedAt(),
                fetched.getUpdatedAt(),
                fetched.isActive()
        );
    }

    public CustomerResponse delete (UUID customerId){
        Customer fetched = customerRepository
                .findById(customerId)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("No Customer found with id " + customerId));

        fetched.setUpdatedAt(LocalDateTime.now());
        fetched.setActive(false);

        customerRepository.save(fetched);

        return new CustomerResponse(
                fetched.getId(),
                fetched.getFullName(),
                fetched.getEmail(),
                fetched.getPhone(),
                fetched.getCreatedAt(),
                fetched.getUpdatedAt(),
                fetched.isActive()
        );
    }
}
