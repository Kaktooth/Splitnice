package com.example.splitwise;

import com.example.splitwise.model.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class SplitwiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitwiseApplication.class, args);

        Account ac1 = Account.builder().id(1).username("asd").phone("098").email("qwe").moneyAmount(new BigDecimal(1)).build();
        Account ac2 = Account.builder().id(1).username("asd").phone("098").email("qwe").moneyAmount(new BigDecimal(1)).build();

        System.out.println(ac1.equals(ac2));
    }
}
