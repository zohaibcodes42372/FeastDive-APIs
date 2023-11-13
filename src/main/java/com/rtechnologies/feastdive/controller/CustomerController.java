package com.rtechnologies.feastdive.controller;

import com.rtechnologies.feastdive.dto.AuthenticationRequest;
import com.rtechnologies.feastdive.dto.AuthenticationResponse;
import com.rtechnologies.feastdive.model.Customer;
import com.rtechnologies.feastdive.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/authenticate")
    @ApiOperation(value = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully authenticated", response = AuthenticationResponse.class),
            @ApiResponse(code = 404, message = "Invalid email or password")
    })
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.authenticate(authenticationRequest));
    }
    @PostMapping("/create")
    @ApiOperation(value = "Create a new customer", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer created successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @PutMapping("/update/{email}")
    @ApiOperation(value = "Update an existing customer", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated successfully"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    public ResponseEntity<?> updateCustomer(@PathVariable String email, @RequestBody Customer updatedCustomer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(email,updatedCustomer));
    }

    @GetMapping("/get/{email}")
    @ApiOperation(value = "Get customer by email", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer retrieved successfully"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    public ResponseEntity<?> getCustomer(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(email));
    }

    @PutMapping("/change-password/{email}")
    @ApiOperation(value = "Change customer password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Password changed successfully"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    public ResponseEntity<?> changePassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        customerService.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.status(HttpStatus.CREATED).body("Password changed successfully");
    }

}

