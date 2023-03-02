package com.veinsmoke.webidbackend.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface SuperUserRepository<T> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
}
