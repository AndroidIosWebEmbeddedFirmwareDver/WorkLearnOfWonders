package com.spring.boot.demo.controller;


import com.spring.boot.demo.entity.Account;
import com.spring.boot.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@EnableAutoConfiguration

@RequestMapping("/system")
public class SystemController {

    @Autowired
    AccountRepository accountRepository;


    @PostMapping(path = "/test")
    public String index(String currentAccount) {
        System.out.println("go into Controller");

        Account account ;
//        for (int i=0;i<1000000;i++)
        {
            account=accountRepository.findOneByAccount(currentAccount);
            System.out.println(account);
            if (null != account) {
                Account account1=new Account();
                account1.copy(account);
                account1.setAccount(UUID.randomUUID().toString().replace("-",""));
                account1.setId(account1.getId() + 1);
                currentAccount=account1.getAccount();
                accountRepository.save(account1);
            }
        }
        return "aaaaaaaaaa";
    }


}
