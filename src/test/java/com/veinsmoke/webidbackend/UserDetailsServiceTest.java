package com.veinsmoke.webidbackend;


import com.veinsmoke.webidbackend.model.Admin;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.AdminRepository;
import com.veinsmoke.webidbackend.repository.ClientRepository;
import com.veinsmoke.webidbackend.security.UserDetailsServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsServiceImp userDetailsService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AdminRepository adminRepository;

    @Test
    void should_return_valid_client_matching_email_and_type() {

        // Arrange
        String email = "client@test.com";
        Client client = new Client();
        client.setEmail(email);
        client.setPassword("password");

        String emailAndType = email+":client";
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));

        // Act
        UserDetails user = userDetailsService.loadUserByUsername(emailAndType);

        // Assert
        assertNotNull(user);
        assertEquals(user.getUsername(), email);
        assertEquals(user.getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("CLIENT")));

    }

    @Test
    void should_return_valid_admin_matching_email_and_type() {

        // Arrange
        String email = "admin@test.com";
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword("password");

        String emailAndType = email+":admin";
        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        // Act
        UserDetails user = userDetailsService.loadUserByUsername(emailAndType);

        // Assert
        assertNotNull(user);
        assertEquals(user.getUsername(), email);
        assertEquals(user.getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

    }

    @Test
    void should_throw_UsernameNotFoundException() {
        // Arrange
        String emailAndType = "test@email.com:invalid_type";

        // Act && Assert
        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(emailAndType));
        assertEquals("User not found", exception.getMessage());
    }


}
