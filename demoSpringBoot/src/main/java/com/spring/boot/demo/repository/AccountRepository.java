package com.spring.boot.demo.repository;


import com.spring.boot.demo.entity.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findOneByAccount(String account);
//    <S extends Account> S save(S s);
}
