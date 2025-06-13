package org.voidcounter.cse813_paas_lab.user_registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.voidcounter.cse813_paas_lab.user_registration.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}

