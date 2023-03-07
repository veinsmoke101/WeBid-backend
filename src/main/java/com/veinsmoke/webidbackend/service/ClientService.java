package com.veinsmoke.webidbackend.service;

import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional( readOnly = true )
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> findByVerificationCodeAndEmail(String verificationCode, String email) {
        return clientRepository.findByVerificationCodeAndEmail(verificationCode, email);
    }

}
