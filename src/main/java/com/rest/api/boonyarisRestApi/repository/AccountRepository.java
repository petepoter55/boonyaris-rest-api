package com.rest.api.boonyarisRestApi.repository;

import com.rest.api.boonyarisRestApi.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);

    Page<Account> findAll(Specification<Account> condition, Pageable pageable);
}
