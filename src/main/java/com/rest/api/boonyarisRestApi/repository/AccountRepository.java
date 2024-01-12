package com.rest.api.boonyarisRestApi.repository;

import com.rest.api.boonyarisRestApi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
