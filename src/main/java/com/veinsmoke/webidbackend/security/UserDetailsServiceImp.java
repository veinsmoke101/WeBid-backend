package com.veinsmoke.webidbackend.security;

import com.veinsmoke.webidbackend.model.Admin;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.AdminRepository;
import com.veinsmoke.webidbackend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailAndType) throws UsernameNotFoundException {
        String email = emailAndType.split(":")[0];
        String type = emailAndType.split(":")[1];

        switch (type) {
            case "client" -> {
                Client client = clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Client not found"));
                return new User(email, client.getPassword(), Collections.singleton(new SimpleGrantedAuthority("CLIENT")));
            }
            case "admin" -> {
                Admin admin =  adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
                return new User(email, admin.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

            }
            default -> throw new UsernameNotFoundException("User not found");
        }
    }
}
