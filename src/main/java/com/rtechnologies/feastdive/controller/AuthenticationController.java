package com.rtechnologies.feastdive.controller;

import com.rtechnologies.feastdive.config.JwtUtil;
import com.rtechnologies.feastdive.dto.AuthenticationRequest;
import com.rtechnologies.feastdive.dto.AuthenticationResponse;
import com.rtechnologies.feastdive.model.Customer;
import com.rtechnologies.feastdive.service.CustomerService;
import com.rtechnologies.feastdive.service.RestaurantService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

//    @Value("${jwt.header}")
//    private String tokenHeader;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private RestaurantService restaurantService;
//
//    @PostMapping("/authenticate")
//    @ApiOperation(value = "Create authentication token", response = AuthenticationResponse.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Authentication successful", response = AuthenticationResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized"),
//            @ApiResponse(code = 404, message = "User not found"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
//        final UserDetails userDetails  = customerService.loadUserByUsername(authenticationRequest.getEmail());
//        final String token = jwtUtil.generateToken(userDetails.getUsername());
//        return ResponseEntity.ok(new AuthenticationResponse(token));
//    }
//
//    private void authenticate(String email, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        } catch (Exception e) {
//            throw new Exception("Incorrect username or password", e);
//        }
//    }
}
