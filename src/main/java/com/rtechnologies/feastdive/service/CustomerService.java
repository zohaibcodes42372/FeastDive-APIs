package com.rtechnologies.feastdive.service;

import com.rtechnologies.feastdive.dto.AuthenticationRequest;
import com.rtechnologies.feastdive.dto.AuthenticationResponse;
import com.rtechnologies.feastdive.dto.RestaurantResponse;
import com.rtechnologies.feastdive.model.Customer;
import com.rtechnologies.feastdive.model.Restaurant;
import com.rtechnologies.feastdive.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@Slf4j
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Optional<Customer> customer = customerRepository.findByEmailAndPassword(authenticationRequest.getEmail(),
                                                                                authenticationRequest.getPassword());

        if(!customer.isPresent()) {
            throw new NotFoundException("Invalid email or password");
        }
        return mapToAuthenticationResponse(customer.get());
    }
    public Customer createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if(!existingCustomer.isPresent()) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            return customerRepository.save(customer);
        } else{
            throw new RuntimeException("Account already exists");
        }
    }

    public Customer updateCustomer(String email, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);

        if(existingCustomer.isPresent()) {
            updatedCustomer.setId(existingCustomer.get().getId());
            updatedCustomer.setPassword(existingCustomer.get().getPassword());
            updatedCustomer.setPhone(existingCustomer.get().getPhone());
            return customerRepository.save(updatedCustomer);
        }

        throw new NotFoundException("Customer not found");
    }

    public Customer getCustomer(String email) throws NotFoundException {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (!customer.isPresent()) {
            throw new NotFoundException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, customer.get().getPassword())) {
            throw new RuntimeException("Incorrect old password");
        }

        customer.get().setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (!customer.isPresent()) {
            throw new NotFoundException("User not found");
        }
        return new User(customer.get().getEmail(), customer.get().getPassword(), emptyList());
    }

    private AuthenticationResponse mapToAuthenticationResponse(Customer customer) {
        return AuthenticationResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }
}
