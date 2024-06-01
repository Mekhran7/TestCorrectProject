package com.test.testtask.security;
import lombok.*;

@RequiredArgsConstructor
@Getter
public class AuthenticationResponse {
    private final String jwt;
}
