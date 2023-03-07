package com.veinsmoke.webidbackend.repository;

import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.base.SuperUserRepository;

import java.util.Optional;

public interface ClientRepository extends SuperUserRepository<Client> {
    Optional<Client> findByVerificationCodeAndEmail(String verificationCode, String email);
}