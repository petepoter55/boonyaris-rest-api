package com.rest.api.boonyarisRestApi.repository;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.model.request.AccountSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

public class AccountSpecification {
    public static Specification<Account> condition(AccountSearchRequest accountSearchRequest) {
        Specification<Account> spec = where(usernameCondition(accountSearchRequest.getUsername()));

        if (!StringUtils.isEmpty(accountSearchRequest.getFirstname())) {
            spec = spec.and(firstnameCondition(accountSearchRequest.getFirstname()));
        }
        if (!StringUtils.isEmpty(accountSearchRequest.getLastname())) {
            spec = spec.and(lastnameCondition(accountSearchRequest.getLastname()));
        }
        if (!StringUtils.isEmpty(accountSearchRequest.getRole())) {
            spec = spec.and(roleCondition(accountSearchRequest.getRole()));
        }
        if (!StringUtils.isEmpty(accountSearchRequest.getEmail())) {
            spec = spec.and(emailCondition(accountSearchRequest.getEmail()));
        }

//        spec = spec.and(orderByCreateDateDesc());

        return spec;
    }

    private static Specification<Account> usernameCondition(String username) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

    private static Specification<Account> firstnameCondition(String firstname) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");
    }

    private static Specification<Account> lastnameCondition(String lastname) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");
    }

    private static Specification<Account> roleCondition(String role) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("role"), role);
    }

    private static Specification<Account> emailCondition(String email) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

//    private static Specification<Account> orderByCreateDateDesc() {
//        return (root, query, criteriaBuilder) -> {
//            query.orderBy(criteriaBuilder.desc(root.get("createDateTime")));
//            return criteriaBuilder.conjunction();
//        };
//    }
}
