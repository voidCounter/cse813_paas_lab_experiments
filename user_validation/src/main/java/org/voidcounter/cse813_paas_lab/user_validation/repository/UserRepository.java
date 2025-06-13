package org.voidcounter.cse813_paas_lab.user_validation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.voidcounter.cse813_paas_lab.user_validation.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

