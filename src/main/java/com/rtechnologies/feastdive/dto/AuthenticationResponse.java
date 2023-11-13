package com.rtechnologies.feastdive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthenticationResponse {
    private long id;
    private String fullName;
    private String email;
    private String phone;
}
